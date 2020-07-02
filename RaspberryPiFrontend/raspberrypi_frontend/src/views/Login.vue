<template>
    <div class="col-md-12">
        <div class="card card-container">
            <img
                    id="profile-img"
                    src="//ssl.gstatic.com/accounts/ui/avatar_2x.png"
                    class="profile-img-card"
            />
            <form name="form" @submit.prevent="handleLogin">
                <div class="form-group">
                    <label for="username">Username</label>
                    <input
                            v-model="user.username"
                            v-validate="'required'"
                            type="text"
                            class="form-control"
                            id="username"
                    />
                    <div
                            v-if="errors.has('username')"
                            class="alert alert-danger"
                            role="alert"
                    >Username is required!
                    </div>
                </div>
                <div class="form-group">
                    <label for="password">Password</label>
                    <input
                            v-model="user.password"
                            v-validate="'required'"
                            type="password"
                            class="form-control"
                            id="password"
                    />
                    <div
                            v-if="errors.has('password')"
                            class="alert alert-danger"
                            role="alert"
                    >Password is required!
                    </div>
                </div>
                <div class="form-group">
                    <button class="btn btn-primary btn-block" :disabled="loading">
                        <span v-show="loading" class="spinner-border spinner-border-sm"></span>
                        <span>Login</span>
                    </button>
                </div>
                <div class="form-group">
                    <div v-if="message" class="alert alert-danger" role="alert">{{message}}</div>
                </div>
            </form>
        </div>
    </div>
</template>

<script>
    import User from '../models/user';
    import AuthService from "../services/AuthService";
    import Router from '../router';
    import {store} from '../store'


    export default {
        name: 'Login',
        data() {
            return {
                user: new User('', ''),
                loading: false,
                message: ''
            };
        },
        computed: {
            loggedIn() {
                return this.$store.state.authenticated;
            }
        },
        created() {
            if (this.loggedIn) {
                this.$router.push('/home');
            }
        },
        methods: {
            handleLogin() {
                this.loading = true;
                this.$validator.validateAll().then(isValid => {
                    if (!isValid) {
                        this.loading = false;
                        return;
                    }

                    if (this.user.username && this.user.password) {
                        AuthService.login(this.user)
                            .then(function (response) {
                                store.commit("setUser", response);
                                store.commit("setAccess", response.accessLevel);
                                console.log("ROLES" + response.accessLevel)
                                Router.push("/");
                            })
                            .catch(function (error) {
                                if (error.response) {
                                    console.log(error.response.data);
                                    console.log(error.response.status);
                                    console.log(error.response.headers);
                                }
                                if(error.response.status == 401){
                                    alert("Unauthorized.  Username or Password may be bad.")
                                }
                            });
                        this.loading = false;
                    }
                });
            }
        }
    };
</script>

<style scoped>
    label {
        display: block;
        margin-top: 10px;
    }

    .card-container.card {
        max-width: 350px !important;
        padding: 40px 40px;
    }

    .card {
        background-color: #f7f7f7;
        padding: 20px 25px 30px;
        margin: 0 auto 25px;
        margin-top: 50px;
        -moz-border-radius: 2px;
        -webkit-border-radius: 2px;
        border-radius: 2px;
        -moz-box-shadow: 0px 2px 2px rgba(0, 0, 0, 0.3);
        -webkit-box-shadow: 0px 2px 2px rgba(0, 0, 0, 0.3);
        box-shadow: 0px 2px 2px rgba(0, 0, 0, 0.3);
    }

    .profile-img-card {
        width: 96px;
        height: 96px;
        margin: 0 auto 10px;
        display: block;
        -moz-border-radius: 50%;
        -webkit-border-radius: 50%;
        border-radius: 50%;
    }
</style>