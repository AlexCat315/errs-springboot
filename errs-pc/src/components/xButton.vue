<script lang="js" setup>
import { ref, defineProps } from "vue";
// 接受父组件传递的 props
// text 和 icon 都是字符串类型
const props = defineProps({
    text: {
    type: String,
    default: "Button"
    },
    icon: {
    type: String,
    default: ""
    },
    size:{
        type: String,
        default: "16px"
    }
});
const text = ref(props.text);
const icon = ref(props.icon);
</script>

<template>
<button class="button">
    <img :style="{height: props.size, width: props.size}" style="margin-right: 3px;" v-if="icon !== ''" :src="icon" alt="icon" class="svg-icon" />
  <span class="lable">{{ text }}</span>
</button>
</template>

<style lang="css" scoped>
.button {
  display: flex;
  justify-content: center;
  align-items: center;
  padding: 6px 12px;
  gap: 8px;
  height: 36px;
  width: 120px;
  border: none;
  background: black;
  border-radius: 20px;
  cursor: pointer;
}

.lable {
  line-height: 20px;
  font-size: 17px;
  color: white;
  font-family: sans-serif;
  letter-spacing: 1px;
}

.button:hover {
  background: grey;
}

.button:hover .svg-icon {
  animation: spin 2s linear infinite;
}

@keyframes spin {
  0% {
    transform: rotate(0deg);
  }

  100% {
    transform: rotate(360deg);
  }
}

</style>