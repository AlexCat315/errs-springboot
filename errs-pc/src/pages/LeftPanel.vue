<script setup lang="js">
import Search from '../components/search.vue';
import {ref} from "vue";

const searchValue = ref('');

const handleSearch = (value) => {
  searchValue.value = value;
  console.log(searchValue.value);
};
</script>

<template>
<div style="margin-top: 20px;"> 
    <Search style="margin-top: 30px;" @update:search="handleSearch" />
   <p>搜索内容是: {{ searchValue }}</p>
</div>


</template>

<style scoped>

</style>