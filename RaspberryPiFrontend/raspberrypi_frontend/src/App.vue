<template>
  <div id="app">
    <component :is="layout">
    <div class="container">
      <div class="wrapper">
        <router-view/>
      </div>
    </div>
    <Footer/>
    </component>
  </div>
</template>

<script>
  import Footer from "./components/Shared/Footer";

  const default_layout = 'default';

  export default {
    components: {Footer},
    computed: {
      layout() {
        return (this.$route.meta.layout || default_layout) + '-layout';
      }
    }
  }
</script>


<style lang="scss">
  #app {
    font-family: Avenir, Helvetica, Arial, sans-serif;
    -webkit-font-smoothing: antialiased;
    -moz-osx-font-smoothing: grayscale;
    text-align: center;
    color: #2c3e50;
  }
</style>