<template>
    <div class="headerContainer">
        <nav class="navbar navbar-expand-lg sticky-top navbar-dark bg-dark">
            <a class="navbar-brand" href="/">
                <i class="fas fa-robot"></i>
                SloshBot
            </a>
            <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#headerNavbar"
                    aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
            </button>

            <div class="collapse navbar-collapse" id="headerNavbar">
                <ul class="navbar-nav mr-auto">
                    <li class="nav-item active">
                        <a class="nav-link" href="/">Home</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="#">Make a Drink</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="#">Browse Drinks</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="#" v-show="this.$store.getters.isModerator">Stats</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="#" v-show="this.$store.getters.isAdmin">Admin</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="#">Account</a>
                    </li>
                </ul>
                <button class="btn btn-outline-danger" v-on:click="logout">Logout</button>
            </div>
        </nav>
    </div>
</template>

<script>
    export default {
        name: "Header",
        components: {},
        data() {
            return {};
        },
        methods: {
            logout(){
                this.$store.commit("logout");
                this.$router.push('/login');
            }
        },
        computed: {}

    }
</script>

<style scoped lang="scss">
</style>