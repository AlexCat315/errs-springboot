<script setup lang="js">

const props = defineProps({
  width: {
    type: String,
    default: "100%"
  },
  height: {
    type: String,
    default: "25px"
  }
});


/**
 * 向父组件发送搜索输入内容
 */
// 定义自定义事件
const emit = defineEmits(['update:search']);

const emitSearch = (event) => {
  // 获取输入框的值
  const value = event.target.value;
  // 触发自定义事件，将值传递给父组件
  emit('update:search', value);
};

</script>

<template>

  <div class="group">
    <svg aria-hidden="true" class="icon" viewBox="0 0 24 24">
      <g>
        <path
            d="M21.53 20.47l-3.66-3.66C19.195 15.24 20 13.214 20 11c0-4.97-4.03-9-9-9s-9 4.03-9 9 4.03 9 9 9c2.215 0 4.24-.804 5.808-2.13l3.66 3.66c.147.146.34.22.53.22s.385-.073.53-.22c.295-.293.295-.767.002-1.06zM3.5 11c0-4.135 3.365-7.5 7.5-7.5s7.5 3.365 7.5 7.5-3.365 7.5-7.5 7.5-7.5-3.365-7.5-7.5z"></path>
      </g>
    </svg>
    <input :style="{width: props.width, height: props.height }"

           class="input" placeholder="Search" type="search" @input="emitSearch">
  </div>

</template>

<style scoped>
.group {
  display: flex;
  line-height: 28px;
  align-items: center;
  position: relative;
  max-width: 190px;
}

.input {
  width: 100%;
  line-height: 28px;
  padding: 0 1rem 0 2.5rem;
  border: 2px solid transparent;
  border-radius: 8px;
  outline: none;
  background-color: #f3f3f4;
  color: #0d0c22;
  transition: .3s ease;
  margin-left: 12px;
}

.input::placeholder {
  color: #9e9ea7;
}

.input:focus, input:hover {
  outline: none;
  border-color: rgba(21, 226, 75, 0.4);
  background-color: #fff;
  box-shadow: 0 0 0 4px rgba(22, 131, 51, 0.1);
}

.icon {
  position: absolute;
  left: 1rem;
  fill: #9e9ea7;
  width: 1rem;
  height: 1rem;
}
</style>