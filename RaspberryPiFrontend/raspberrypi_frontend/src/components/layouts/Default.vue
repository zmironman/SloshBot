<template>
    <div id="app-header-layout">
        <Header></Header>

        <slot/>
    </div>
</template>

<script>
    import Header from '../Shared/Header'
    export default {
        name: "HeaderLayout",
        components: {Header}
    }
</script>

<style scoped>

</style>