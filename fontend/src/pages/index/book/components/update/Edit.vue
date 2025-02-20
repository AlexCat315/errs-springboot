<script setup lang="ts"></script>

<template>
    <button class="btn">
        <svg t="1740045580852" class="icon" viewBox="0 0 1024 1024" version="1.1" xmlns="http://www.w3.org/2000/svg"
            p-id="3526" width="200" height="200">
            <path
                d="M389.82 521.998l43.902-20.364 98.64 97.88L512 644.18c20.954 248.476-139.92 305.454-386.5 305.454-21.16 0-36.94-2.628-47.916-10.202-32.134-22.176 1.02-69.46 14.846-105.688C135.11 721.886 88.66 522 389.818 522z"
                fill="#FF934A" p-id="3527"></path>
            <path
                d="M943.584 256.95l-266.028 338.9c-15.924 20.292-37.836 35.096-63.168 39.462-25.414 4.38-60.908 8.866-102.388 8.866 0 0-10.814-27.286-51.316-68.014C420.18 535.44 389.818 522 389.818 522c0-43.524 5.784-80.3 11.16-105.118 4.908-22.59 18.672-41.776 36.716-56.104L778.09 90.508c11.038-8.766 23.58-15.872 37.632-16.424 22.97-0.904 61.762 4.896 100.534 43.9 38.794 39.006 44.556 78.012 43.66 101.116-0.55 14.136-7.616 26.746-16.332 37.85z"
                fill="#FCBF28" p-id="3528"></path>
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
    content: "删除";
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

.btn:hover>.icon {
    transform: scale(1.2);
}



.btn:hover::after {
    visibility: visible;
    opacity: 1;
    top: 105%;
}
</style>
