<script setup lang="ts">

</script>

<template>
    <div style="display: flex;">
        <div class="custom-loader">
        </div>
    </div>


</template>

<style lang="css" scoped>
.custom-loader {
    width: 50px;
    height: 50px;
    display: grid;
    border-radius: 50%;
    background: linear-gradient(0deg, #3498db 50%, #e74c3c 0) center/4px 100%,
        linear-gradient(90deg, #2ecc71 50%, #f39c12 0) center/100% 4px;
    background-repeat: no-repeat;
    animation: s3 1s infinite steps(12);
}

.custom-loader::before,
.custom-loader::after {
    content: "";
    grid-area: 1/1;
    border-radius: 50%;
    background: inherit;
    opacity: 0.915;
    transform: rotate(30deg);
}

.custom-loader::after {
    opacity: 0.83;
    transform: rotate(60deg);
}

@keyframes s3 {
    100% {
        transform: rotate(1turn)
    }
}
</style>