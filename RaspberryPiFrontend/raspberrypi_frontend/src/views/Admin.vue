<template>
    <div class="admin">
        <Unauthorized v-show="!$store.getters.isAdmin"/>
        <div id="AdminContainer" v-show="$store.getters.isAdmin">
            <p>Admin page</p>
        </div>
    </div>
</template>

<script>
    import Unauthorized from "../components/Shared/Unauthorized";

    export default {
        name: "Admin",
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