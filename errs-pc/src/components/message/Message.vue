<script setup lang="ts">
const props = defineProps({
    message: {
        type: String,
        required: true
    },
    type:{
        type: String,
        required: true
    },
    width:{
        type: String,
        default: '300px'
    }
})
</script>

<template>
<div class="popup-container">
    <!-- success message -->
  <div v-if="props.type ==='success'" :style="{width: props.width}" class="popup success-popup">
    <div class="popup-icon success-icon">
      <svg
        xmlns="http://www.w3.org/2000/svg"
        viewBox="0 0 24 24"
        class="success-svg"
      >
        <path
          fill-rule="evenodd"
          d="m12 1c-6.075 0-11 4.925-11 11s4.925 11 11 11 11-4.925 11-11-4.925-11-11-11zm4.768 9.14c.0878-.1004.1546-.21726.1966-.34383.0419-.12657.0581-.26026.0477-.39319-.0105-.13293-.0475-.26242-.1087-.38085-.0613-.11844-.1456-.22342-.2481-.30879-.1024-.08536-.2209-.14938-.3484-.18828s-.2616-.0519-.3942-.03823c-.1327.01366-.2612.05372-.3782.1178-.1169.06409-.2198.15091-.3027.25537l-4.3 5.159-2.225-2.226c-.1886-.1822-.4412-.283-.7034-.2807s-.51301.1075-.69842.2929-.29058.4362-.29285.6984c-.00228.2622.09851.5148.28067.7034l3 3c.0983.0982.2159.1748.3454.2251.1295.0502.2681.0729.4069.0665.1387-.0063.2747-.0414.3991-.1032.1244-.0617.2347-.1487.3236-.2554z"
          clip-rule="evenodd"
        ></path>
      </svg>
    </div>
    <div class="success-message">{{ props.message }}</div>
  </div>

  <!-- alert message -->
  <div v-if="props.type === 'alert'" :style="{width: props.width}"  class="popup alert-popup">
    <div class="popup-icon alert-icon">
      <svg
        class="alert-svg"
        xmlns="http://www.w3.org/2000/svg"
        viewBox="0 0 20 20"
        aria-hidden="true"
      >
        <path
          fill-rule="evenodd"
          d="M8.257 3.099c.765-1.36 2.722-1.36 3.486 0l5.58 9.92c.75 1.334-.213 2.98-1.742 2.98H4.42c-1.53 0-2.493-1.646-1.743-2.98l5.58-9.92zM11 13a1 1 0 11-2 0 1 1 0 012 0zm-1-8a1 1 0 00-1 1v3a1 1 0 002 0V6a1 1 0 00-1-1z"
          clip-rule="evenodd"
        ></path>
      </svg>
    </div>
    <div class="alert-message">{{ props.message }}</div>

  </div>
  <!-- error message -->
  <div v-if="props.type === 'error'" :style="{width: props.width}"  class="popup error-popup">
    <div class="popup-icon error-icon">
      <svg
        class="error-svg"
        xmlns="http://www.w3.org/2000/svg"
        viewBox="0 0 20 20"
        aria-hidden="true"
      >
        <path
          fill-rule="evenodd"
          d="M10 18a8 8 0 100-16 8 8 0 000 16zM8.707 7.293a1 1 0 00-1.414 1.414L8.586 10l-1.293 1.293a1 1 0 101.414 1.414L10 11.414l1.293 1.293a1 1 0 001.414-1.414L11.414 10l1.293-1.293a1 1 0 00-1.414-1.414L10 8.586 8.707 7.293z"
          clip-rule="evenodd"
        ></path>
      </svg>
    </div>
    <div class="error-message">{{ props.message }}</div>
  </div>
  <!-- info message -->
  <div v-if="props.type === 'info'" :style="{width: props.width}"  class="popup info-popup">
    <div class="popup-icon info-icon">
      <svg
        aria-hidden="true"
        viewBox="0 0 20 20"
        xmlns="http://www.w3.org/2000/svg"
        class="info-svg"
      >
        <path
          clip-rule="evenodd"
          d="M18 10a8 8 0 11-16 0 8 8 0 0116 0zm-7-4a1 1 0 11-2 0 1 1 0 012 0zM9 9a1 1 0 000 2v3a1 1 0 001 1h1a1 1 0 100-2v-3a1 1 0 00-1-1H9z"
          fill-rule="evenodd"
        ></path>
      </svg>
    </div>
    <div class="info-message">{{ props.message }}</div>
  </div>
</div>


</template>

<style lang="css" scoped>
.popup-container {
  z-index: 9999 !important;
}

.popup {
  margin: 10px;
  box-shadow: 4px 4px 10px -10px rgba(0, 0, 0, 1);
  align-items: center;
  display: flex;
  border-radius: 10px;
  padding: 5px 0;
  font-weight: 300;
}
.popup svg {
  width: 1.25rem;
  height: 1.25rem;
}
.popup-icon svg {
  margin: 5px;
  display: flex;
  align-items: center;
}


/* SUCCESS */
.success-popup {
  background-color: #edfbd8;
  border: solid 1px #84d65a;
}
.success-icon path {
  fill: #84d65a;
}
.success-message {
  color: #2b641e;
}

/* ALERT */
.alert-popup {
  background-color: #fefce8;
  border: solid 1px #facc15;
}
.alert-icon path {
  fill: #facc15;
}
.alert-message {
  color: #ca8a04;
  
}

/* ERROR */

.error-popup {
  background-color: #fef2f2;
  border: solid 1px #f87171;
}
.error-icon path {
  fill: #f87171;
}
.error-message {
  color: #991b1b;
}

/* INFO */

.info-popup {
  background-color: #eff6ff;
  border: solid 1px #1d4ed8;
}
.info-icon path {
  fill: #1d4ed8;
}
.info-message {
  color: #1d4ed8;
}




</style>