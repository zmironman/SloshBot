<template>
    <div class="browseDrinks">
        <Unauthorized v-show="!$store.getters.isAuthenticated"/>
        <div id="BrowseDrinksContainer" v-show="$store.getters.isAuthenticated">
            <p>Browse Drinks page</p>
        </div>
    </div>
</template>

<script>
    import Unauthorized from "../components/Shared/Unauthorized";
    export default {
        name: "BrowseDrinks",
        components: {Unauthorized},
        data() {
            return {};
        },
        methods: {},
        computed: {
        }
    }
</script>

<style scoped>

</style>