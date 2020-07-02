<template>
    <div id="NoHeaderLayout">
        <nav class="navbar navbar-expand-lg sticky-top navbar-dark bg-dark">
            <a class="navbar-brand" href="/">
                <i class="fas fa-robot"></i>
                SloshBot
            </a>
        </nav>
        <slot/>
    </div>
</template>

<script>
    export default {
        name: "Default"
    }
</script>

<style scoped>

</style>