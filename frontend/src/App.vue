<script setup>
</script>

<template>
  <el-config-provider>
    <router-view />
  </el-config-provider>
</template>

<style>
/* Basic transition for route changes if needed */
.fade-enter-active,
.fade-leave-active {
  transition: opacity 0.2s ease;
}

.fade-enter-from,
.fade-leave-to {
  opacity: 0;
}
</style>
