<template>
    <div class="makeADrink">
        <Unauthorized v-show="!$store.getters.isAuthenticated"/>
        <div id="MakeADrinkContainer" v-show="$store.getters.isAuthenticated">
            <p>Make A Drink page</p>
        </div>
    </div>
</template>

<script>
    import Unauthorized from "../components/Shared/Unauthorized";
    export default {
        name: "MakeADrink",
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