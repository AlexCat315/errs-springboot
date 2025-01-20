<script setup lang="ts">
import { inject, Ref, ref } from 'vue';


const globalTheme = inject<Ref<string>>("globalTheme");
if (!globalTheme) {
  console.error("globalTheme is not provided.");
}


// 使用 v-model 绑定值
const isChecked = ref(false);

// 使用 ref 绑定 DOM 元素
const checkboxRef = ref<HTMLInputElement | null>(null);

// 过 ref 获取 DOM 元素
const logCheckboxValue = () => {
  // localStorage.setItem('gpu', checkboxRef.value?.checked ? 'true' : 'false');
  // invoke("set_gpu_acceleration", {
  //   enabled: checkboxRef.value?.checked
  // })
};

</script>

<template>
  <div class="checkbox-wrapper-33">
    <label class="checkbox">
      <input class="checkbox__trigger visuallyhidden" type="checkbox" v-model="isChecked" @change="logCheckboxValue"
        ref="checkboxRef" />
      <span class="checkbox__symbol">
        <svg aria-hidden="true" class="icon-checkbox" width="13px" height="13px" viewBox="0 0 28 28" version="1"
          xmlns="http://www.w3.org/2000/svg">
          <path d="M4 14l8 7L24 7"></path>
        </svg>
      </span>
      <p class="checkbox__textwrapper" :style="{ color: globalTheme === 'light' ? 'black' : '#bfc3cb' }">
        一键隐私
      </p>
    </label>
  </div>
</template>


<style scoped>
.checkbox-wrapper-33 {
  --s-xsmall: 0.625em;
  --s-small: 1.2em;
  --border-width: 1px;
  --c-primary: #265ec5;
  --c-primary-20-percent-opacity: rgba(83, 141, 216, 0.2);
  --c-primary-10-percent-opacity: rgba(88, 139, 221, 0.1);
  --t-base: 0.4s;
  --t-fast: 0.2s;
  --e-in: ease-in;
  --e-out: cubic-bezier(0.11, 0.29, 0.18, 0.98);
}

.checkbox-wrapper-33 .visuallyhidden {
  border: 0;
  clip: rect(0 0 0 0);
  height: 1px;
  margin: -1px;
  overflow: hidden;
  padding: 0;
  position: absolute;
  width: 1px;
}

.checkbox-wrapper-33 .checkbox {
  display: flex;
  align-items: center;
  justify-content: flex-start;
}

.checkbox-wrapper-33 .checkbox+.checkbox {
  margin-top: var(--s-small);
}

.checkbox-wrapper-33 .checkbox__symbol {
  display: inline-block;
  display: flex;
  margin-right: calc(var(--s-small) * 0.7);
  border: var(--border-width) solid var(--c-primary);
  position: relative;
  border-radius: 0.1em;
  width: 1em;
  height: 1em;
  transition: box-shadow var(--t-base) var(--e-out),
    background-color var(--t-base);
  box-shadow: 0 0 0 0 var(--c-primary-10-percent-opacity);
}

.checkbox-wrapper-33 .checkbox__symbol:after {
  content: "";
  position: absolute;
  top: 0.5em;
  left: 0.5em;
  width: 0.3em;
  height: 0.3em;
  background-color: var(--c-primary-20-percent-opacity);
  opacity: 0;
  border-radius: 3em;
  transform: scale(1.1);
  transform-origin: 50% 50%;
}

.checkbox-wrapper-33 .checkbox .icon-checkbox {
  width: 0.9em;
  height: 0.9em;
  margin: auto;
  fill: none;
  stroke-width: 3;
  stroke: currentColor;
  stroke-linecap: round;
  stroke-linejoin: round;
  stroke-miterlimit: 10;
  color: var(--c-primary);
  display: inline-block;
}

.checkbox-wrapper-33 .checkbox .icon-checkbox path {
  transition: stroke-dashoffset var(--t-fast) var(--e-in);
  stroke-dasharray: 30px, 31px;
  stroke-dashoffset: 31px;
}

.checkbox-wrapper-33 .checkbox__textwrapper {
  margin: 0;
  font-family: Arial, Helvetica, sans-serif;
  font-size: 13px;
}

.checkbox-wrapper-33 .checkbox__trigger:checked+.checkbox__symbol:after {
  -webkit-animation: ripple-33 1.5s var(--e-out);
  animation: ripple-33 1.5s var(--e-out);
}

.checkbox-wrapper-33 .checkbox__trigger:checked+.checkbox__symbol .icon-checkbox path {
  transition: stroke-dashoffset var(--t-base) var(--e-out);
  stroke-dashoffset: 0px;
}

.checkbox-wrapper-33 .checkbox__trigger:focus+.checkbox__symbol {
  box-shadow: 0 0 0 0.25em var(--c-primary-20-percent-opacity);
}

@-webkit-keyframes ripple-33 {
  from {
    transform: scale(0);
    opacity: 1;
  }

  to {
    opacity: 0;
    transform: scale(20);
  }
}

@keyframes ripple-33 {
  from {
    transform: scale(0);
    opacity: 1;
  }

  to {
    opacity: 0;
    transform: scale(20);
  }
}
</style>