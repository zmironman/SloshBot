<template>
    <div class="stats">
        <Unauthorized v-show="!$store.getters.isModerator"/>
        <div id="StatsContainer" v-show="$store.getters.isModerator">
            <p>Stats page</p>
        </div>
    </div>
</template>

<script>
    import Unauthorized from "../components/Shared/Unauthorized";
    export default {
        name: "Stats",
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