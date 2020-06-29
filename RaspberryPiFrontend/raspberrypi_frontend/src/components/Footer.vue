<template>
    <div class="footerContainer">
        <div>
            <b-navbar toggleable="lg" type="dark" variant="dark ">

            </b-navbar>
        </div>
    </div>
</template>

<script>
    export default {
        name: 'Footer'
    }
</script>

<style lang="scss">
    #nav {
        padding: 30px;

        a {
            font-weight: bold;
            color: #2c3e50;

            &.router-link-exact-active {
                color: #42b983;
            }
        }
    }
    .footerContainer{
        background-color: darkgrey;
        position: fixed;
        left: 0;
        bottom: 0;
        width: 100%;
    };
</style>