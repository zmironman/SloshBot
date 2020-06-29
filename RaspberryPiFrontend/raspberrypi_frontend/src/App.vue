<template>
  <div id="app">
    <Header/>
    <div class="container">
      <div class="wrapper">
        <router-view/>
      </div>
    </div>
    <Footer/>
  </div>
</template>

<script>
  import Header from "./components/Header";
  import Footer from "./components/Footer";
  export default {
    components: {Footer, Header}
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