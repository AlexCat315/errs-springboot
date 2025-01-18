<script lang="ts" setup>
import {ref} from "vue";
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
        default: "18px"
    },
    backgroundColor:{
        type: String,
        default: "black"
    },
  height:{
    type: String,
    default: "36px"
  },
  width:{
      type: String,
      default: "120px"
  },
  borderRadius:{
      type: String,
      default: "10px"
  },
  textColor:{
      type: String,
      default: "white"
  }
});
const text = ref(props.text);
const icon = ref(props.icon);
</script>

<template>
<button :style="{backgroundColor: props.backgroundColor, height: props.height, width: props.width, borderRadius: props.borderRadius}" class="button">
    <img v-if="icon !== ''"  :src="icon" :style="{height: props.size, width: props.size}" alt="icon" class="svg-icon" />
  <span :style="{color: props.textColor}" class="liable">{{ text }}</span>
</button>
</template>

<style lang="css" scoped>
.button {
  display: flex;
  padding: 6px 12px;
  gap: 8px;
  border: none;
  cursor: pointer;
  margin-left: 10px;
  margin-right: 10px;
}

.liable {
  line-height: 20px;
  font-size: 17px;
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