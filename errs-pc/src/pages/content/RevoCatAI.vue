<script setup lang="ts">
import { ref,  nextTick, onMounted, watch, onBeforeUnmount } from 'vue'
import { askAi } from "../../net/ai/get"
import MarkdownIt from 'markdown-it'

const md = new MarkdownIt()

interface Message {
    content: string
    isAI: boolean
    timestamp: number
}

interface ChatHistory {
    id: string
    messages: Message[]
    createdAt: number
}

const messages = ref<Message[]>([
    {
        content: '您好！我是RevoCat的AI助手(基于deepseek Api)，可以为您推荐各类文娱内容。试试下面的快捷提问吧！',
        isAI: true,
        timestamp: Date.now()
    }
])

const inputMessage = ref('')
const isLoading = ref(false) 
const error = ref('')
const showHistory = ref(false)
const chatHistories = ref<ChatHistory[]>([])
const showErrorPanel = ref(false)
const currentAiMessage = ref('')
const shouldSaveHistory = ref(false)

const presetPrompts = [
    '推荐近期值得阅读的书籍',
    '有什么适合周末玩的PC游戏？', 
    '推荐治愈系动漫',
    '最近有什么高分电影？',
    '推荐适合团队建设的桌游'
]

const messagesEnd = ref<HTMLElement | null>(null)
const chatWindow = ref<HTMLElement | null>(null)

const scrollToBottom = () => {
    nextTick(() => {
        if (chatWindow.value) {
            chatWindow.value.scrollTop = chatWindow.value.scrollHeight
        }
    })
}

const loadHistories = () => {
    const histories = localStorage.getItem('chatHistories')
    if (histories) {
        chatHistories.value = JSON.parse(histories)
    }
}

const saveToHistory = () => {
    if (messages.value.length <= 1) return

    const newHistory: ChatHistory = {
        id: Date.now().toString(),
        messages: [...messages.value],
        createdAt: Date.now()
    }

    chatHistories.value.unshift(newHistory)
    localStorage.setItem('chatHistories', JSON.stringify(chatHistories.value))
}

const loadHistory = (history: ChatHistory) => {
    messages.value = [...history.messages]
    showHistory.value = false
}

const clearContext = () => {
    if (messages.value.length > 1) {
        saveToHistory()
    }
    messages.value = [messages.value[0]]
}

const deleteHistory = (id: string) => {
    chatHistories.value = chatHistories.value.filter(h => h.id !== id)
    localStorage.setItem('chatHistories', JSON.stringify(chatHistories.value))
}

const sendMessage = async (prompt?: string) => {
    const message = prompt || inputMessage.value.trim();
    if (!message) return;

    try {
        isLoading.value = true;
        error.value = '';
        currentAiMessage.value = '';

        messages.value.push({
            content: message,
            isAI: false,
            timestamp: Date.now()
        });

        messages.value.push({
            content: '',
            isAI: true,
            timestamp: Date.now()
        });

        const token = localStorage.getItem('token');
        const response = await fetch(
            `http://localhost:12345/api/user/ai/chat/stream?question=${encodeURIComponent(message)}`,
            {
                headers: {
                    'Content-Type': 'text/event-stream',
                    Authorization: `Bearer ${token}`
                }
            }
        );

        if (!response.ok) {
            throw new Error('请求失败');
        }

        const reader = response.body?.getReader();
        const decoder = new TextDecoder();

        while (reader) {
            const {value, done} = await reader.read();
            if (done) break;

            const text = decoder.decode(value);
            const lines = text.split('\n');

            for (const line of lines) {
                if (line.startsWith('data: ')) {
                    const content = line.substring(6);
                    currentAiMessage.value += content;
                    messages.value[messages.value.length - 1].content = md.render(currentAiMessage.value);
                    scrollToBottom();
                }
            }
        }

    } catch (err) {
        handleError(err instanceof Error ? err.message : '请求失败');
    } finally {
        isLoading.value = false;
        inputMessage.value = '';
    }
};

const handleError = (message: string) => {
    error.value = message;
    showErrorPanel.value = true;
    setTimeout(() => {
        showErrorPanel.value = false;
    }, 3000);
};

const usePresetPrompt = (prompt: string) => {
    inputMessage.value = prompt
    sendMessage(prompt)
}

onMounted(() => {
    loadHistories()
    chatWindow.value = document.querySelector('.chat-window')
})

onBeforeUnmount(() => {
    if (messages.value.length > 1) {
        saveToHistory()
    }
})

watch(messages, (newVal) => {
    localStorage.setItem('currentChat', JSON.stringify(newVal));
}, { deep: true });
</script>
<template>
    <div class="ai-container">
        <p v-if="showErrorPanel" class="error-msg">{{ error }}</p>

        <div v-if="showHistory" class="history-drawer">
            <h3>历史对话</h3>
            <div v-if="chatHistories.length === 0" class="no-history">
                暂无历史记录
            </div>
            <div v-else class="history-list">
                <div v-for="history in chatHistories" :key="history.id" class="history-item">
                    <div class="history-info">
                        <span class="history-date">{{ new Date(history.createdAt).toLocaleString() }}</span>
                        <div class="history-actions">
                            <button @click="loadHistory(history)">加载</button>
                            <button @click="deleteHistory(history.id)" class="delete-btn">删除</button>
                        </div>
                    </div>
                </div>
            </div>
            <button @click="showHistory = false" class="close-history">关闭</button>
        </div>

        <div class="toolbar">
            <button @click="showHistory = true" class="tool-btn">历史记录</button>
            <button @click="clearContext" class="tool-btn">清除对话</button>
        </div>

        <div class="chat-window">
            <div v-for="(msg, index) in messages" :key="index" class="message" :class="{ 'ai-message': msg.isAI }">
                <div class="message-content" :class="{ 'user-message': !msg.isAI }">
                    <div v-if="msg.isAI" class="ai-avatar"><img style="width: 40px;height: 40px;"
                            src="../../assets/icons//icon.png" /></div>
                    <div style="margin-top: 20px;" v-if="!msg.isAI" class="user-avatar"><img
                            style="width: 30px;height: 30px;" src="../../assets//img/用户.png" /></div>
                    <div class="text" v-if="!msg.isAI">{{ msg.content }}</div>
                    <div class="text markdown-body" v-else v-html="msg.content"></div>
                </div>
                <div class="timestamp">{{ new Date(msg.timestamp).toLocaleTimeString() }}</div>
            </div>
            <div ref="messagesEnd"></div>
        </div>

        <div v-if="isLoading" class="loading">
            <div class="dot-flashing"></div>
        </div>

        <div class="preset-prompts">
            <button v-for="(prompt, index) in presetPrompts" :key="index" @click.prevent="usePresetPrompt(prompt)"
                class="prompt-btn">
                {{ prompt }}
            </button>
        </div>

        <div class="input-area">
            <input type="text" :value="inputMessage" @input="e => inputMessage = (e.target as HTMLInputElement).value"
                @keyup.enter="sendMessage()" placeholder="输入您想了解的文娱内容..." :disabled="isLoading" />
            <button type="button" @click="sendMessage()" :disabled="isLoading || !inputMessage">
                {{ isLoading ? '发送中...' : '发送' }}
            </button>
        </div>

    </div>
</template>

<style scoped>
* {
    user-select: text !important;
    -webkit-user-select: text !important;
    -moz-user-select: text !important;
    -ms-user-select: text !important;
}

.markdown-body {
    font-family: -apple-system,BlinkMacSystemFont,Segoe UI,Helvetica,Arial,sans-serif;
    font-size: 16px;
    line-height: 1.5;
    word-wrap: break-word;
}

.markdown-body h1 {
    font-size: 2em;
    margin: 0.67em 0;
}

.markdown-body h2 {
    font-size: 1.5em;
    margin-top: 1em;
    margin-bottom: 16px;
    font-weight: 600;
    line-height: 1.25;
}

.markdown-body h3 {
    font-size: 1.25em;
    margin-top: 1em;
    margin-bottom: 16px;
    font-weight: 600;
    line-height: 1.25;
}

.markdown-body p {
    margin-top: 0;
    margin-bottom: 16px;
}

.markdown-body ul {
    padding-left: 2em;
    margin-bottom: 16px;
}

.markdown-body code {
    padding: 0.2em 0.4em;
    margin: 0;
    font-size: 85%;
    background-color: rgba(27,31,35,0.05);
    border-radius: 3px;
}

.ai-container {
    max-width: 800px;
    margin: 2rem auto;
    background: #f5f7fb;
    border-radius: 15px;
    box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
    padding: 1.5rem;
    max-height: 100vh !important;
    font-family: system-ui, -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, Oxygen, Ubuntu, Cantarell, 'Open Sans', 'Helvetica Neue', sans-serif;
    position: relative;
}

.toolbar {
    display: flex;
    gap: 1rem;
    margin-bottom: 1rem;
}

.tool-btn {
    background: #6d5dfc;
    color: white;
    border: none;
    padding: 0.5rem 1rem;
    border-radius: 8px;
    cursor: pointer;
    transition: opacity 0.2s;
}

.history-drawer {
    position: absolute;
    top: 0;
    left: 0;
    right: 0;
    bottom: 0;
    background: white;
    z-index: 100;
    padding: 1.5rem;
    border-radius: 15px;
    display: flex;
    flex-direction: column;
}

.history-list {
    flex: 1;
    overflow-y: auto;
}

.history-item {
    padding: 1rem;
    border-bottom: 1px solid #eee;
}

.history-info {
    display: flex;
    justify-content: space-between;
    align-items: center;
}

.history-date {
    color: #666;
    font-size: 0.9rem;
}

.history-actions {
    display: flex;
    gap: 0.5rem;
}

.history-actions button {
    padding: 0.3rem 0.8rem;
    border-radius: 4px;
    border: none;
    cursor: pointer;
}

.delete-btn {
    background: #ff4444;
    color: white;
}

.close-history {
    margin-top: 1rem;
    padding: 0.5rem;
    background: #6d5dfc;
    color: white;
    border: none;
    border-radius: 8px;
    cursor: pointer;
}

.chat-window {
    height: 60vh;
    overflow-y: auto;
    padding: 1rem;
    background: white;
    border-radius: 10px;
    margin-bottom: 1rem;
}

.message {
    margin: 1rem 0;
    animation: fadeIn 0.3s ease-in;
}

.message-content {
    display: flex;
    align-items: flex-start;
    gap: 0.8rem;
    word-break: break-all;
    word-wrap: break-word;
}

.message-content.user-message {
    flex-direction: row-reverse;
}

.ai-message {
    display: flex;
    justify-content: flex-start;
}

.message:not(.ai-message) {
    display: flex;
    justify-content: flex-end;
}

.text {
    padding: 1rem;
    max-width: 70%;
    line-height: 1.5;
    overflow-wrap: break-word;
}

.ai-message .text {
    background: #e3f2fd;
    border-radius: 15px 15px 15px 3px;
}

.message:not(.ai-message) .text {
    background: #6d5dfc;
    color: white;
    border-radius: 15px 15px 3px 15px;
}

.ai-avatar,
.user-avatar {
    font-size: 1.5rem;
    margin-top: 0.5rem;
}

.timestamp {
    font-size: 0.8rem;
    color: #666;
    margin-top: 0.5rem;
    text-align: right;
}

.preset-prompts {
    display: flex;
    flex-wrap: wrap;
    gap: 0.5rem;
    margin-bottom: 1rem;
}

.prompt-btn {
    background: #f0f4ff;
    border: 1px solid #6d5dfc;
    color: #6d5dfc;
    padding: 0.5rem 1rem;
    border-radius: 20px;
    cursor: pointer;
    transition: all 0.2s;
}

.prompt-btn:hover {
    background: #6d5dfc;
    color: white;
}

.input-area {
    display: flex;
    gap: 1rem;
}

.input-area input {
    flex: 1;
    padding: 1rem;
    border: 1px solid #ddd;
    border-radius: 8px;
    font-size: 1rem;
}

.input-area button {
    background: #6d5dfc;
    color: white;
    border: none;
    padding: 0 2rem;
    border-radius: 8px;
    cursor: pointer;
    transition: opacity 0.2s;
}

.input-area button:disabled {
    opacity: 0.7;
    cursor: not-allowed;
}

.loading {
    text-align: center;
    padding: 1rem;
}

.dot-flashing {
    position: relative;
    width: 10px;
    height: 10px;
    border-radius: 5px;
    background-color: #6d5dfc;
    color: #6d5dfc;
    animation: dotFlashing 1s infinite linear;
}

.dot-flashing::before,
.dot-flashing::after {
    content: '';
    display: inline-block;
    position: absolute;
    top: 0;
}

.dot-flashing::before {
    left: -15px;
    width: 10px;
    height: 10px;
    border-radius: 5px;
    background-color: #6d5dfc;
    color: #6d5dfc;
    animation: dotFlashing 1s infinite linear;
    animation-delay: 0s;
}

.dot-flashing::after {
    left: 15px;
    width: 10px;
    height: 10px;
    border-radius: 5px;
    background-color: #6d5dfc;
    color: #6d5dfc;
    animation: dotFlashing 1s infinite linear;
    animation-delay: 0.2s;
}

@keyframes moveUp {
    0% {
        transform: translateY(0);
    }

    100% {
        transform: translateY(-20px);
    }
}

.error-msg {
    background-color: rgba(0, 0, 0, 0.7);
    color: #fff;
    width: 150px;
    height: 30px;
    display: flex;
    align-items: center;
    justify-content: center;
    font-family: Arial, Helvetica, sans-serif;
    position: fixed;
    z-index: 4;
    left: 50%;
    transform: translateX(-50%);
    margin-top: 30px;
    animation: moveUp 0.4s ease-in-out forwards;
    font-size: 13px;
    border-radius: 8px;
}

@keyframes fadeIn {
    from {
        opacity: 0;
        transform: translateY(10px);
    }

    to {
        opacity: 1;
        transform: translateY(0);
    }
}

@keyframes dotFlashing {
    0% {
        background-color: #6d5dfc;
    }

    50%,
    100% {
        background-color: rgba(109, 93, 252, 0.2);
    }
}
</style>
