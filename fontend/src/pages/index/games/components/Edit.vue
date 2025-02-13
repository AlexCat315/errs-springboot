<script setup lang="ts"></script>

<template>
    <button class="btn">
        <svg
            t="1739434639099"
            class="icon"
            viewBox="0 0 1024 1024"
            version="1.1"
            xmlns="http://www.w3.org/2000/svg"
            p-id="12087"
            width="200"
            height="200"
        >
            <path
                d="M512 948.343028c-240.755367 0-435.925834-195.170466-435.925834-435.925833s195.170466-435.925834 435.925834-435.925834 435.925834 195.170466 435.925834 435.925834-195.170466 435.925834-435.925834 435.925833zM282.938246 626.598382c-5.604761 5.605977-9.091924 13.389151-9.091924 22.045636v62.276335c0 17.125658 13.94987 31.13756 31.13756 31.13756h62.275119c8.594453 0 16.378843-3.549195 22.045635-9.091925L678.634596 443.571564l-106.301925-106.303142-289.394425 289.32996z m480.017104-311.374379l-62.275119-62.275119c-5.603544-5.604761-13.389151-9.091924-22.045635-9.091924-8.655269 0-16.440875 3.487163-22.045635 9.091924L616.359477 293.241616l106.301925 106.303142 40.2307-40.229484c5.604761-5.666793 9.153956-13.451182 9.153956-22.045635s-3.547979-16.378843-9.090708-22.045636z"
                fill="#272536"
                p-id="12088"
            ></path>
        </svg>
    </button>
</template>

<style scoped>
.btn {
    display: flex;
    align-items: center;
    justify-content: center;
    width: 30px;
    height: 30px;
    border-radius: 10px;
    cursor: pointer;
    border: none;
    background-color: transparent;
    position: relative;
    margin-right: -10px;
}

.btn::after {
    content: "修改";
    width: fit-content;
    height: fit-content;
    position: absolute;
    font-size: 11px;
    font-family: "Lucida Sans", "Lucida Sans Regular", "Lucida Grande",
        "Lucida Sans Unicode", Geneva, Verdana, sans-serif;
    opacity: 0;
    visibility: hidden;
    transition: 0.2s linear;
    top: 115%;
}

.icon {
    width: 70px;
    height: 70px;
    transition: 0.2s linear;
}

.icon path {
    transition: 0.2s linear;
}

.btn:hover > .icon {
    transform: scale(1.2);
}

.btn:hover > .icon path {
    fill: red;
}

.btn:hover::after {
    visibility: visible;
    opacity: 1;
    top: 105%;
}
</style>
