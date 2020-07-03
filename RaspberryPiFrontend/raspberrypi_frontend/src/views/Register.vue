<template>
    <div class="col-md-12">
        <div class="card card-container">
            <img
                    id="profile-img"
                    src="//ssl.gstatic.com/accounts/ui/avatar_2x.png"
                    class="profile-img-card"
            />
            <form name="form" @submit.prevent="handleRegister">
                <div v-if="!successful">
                    <div class="form-group" id="NameFormGroup">
                        <label for="name">Name</label>
                        <input
                                v-model="user.name"
                                type="text"
                                class="form-control"
                                id="name"
                        />
                    </div>
                    <div class="form-group" id="UsernameFormGroup">
                        <label for="username">Username</label>
                        <input
                                v-model="user.username"
                                type="text"
                                class="form-control"
                                id="username"
                        />
                    </div>
                    <div class="form-group" id="EmailFormGroup">
                        <label for="email">Email</label>
                        <input
                                v-model="user.email"
                                type="email"
                                class="form-control"
                                id="email"
                        />
                    </div>
                    <div class="form-group" id="PasswordFormGroup">
                        <label for="password">Password</label>
                        <input
                                v-model="user.password"
                                type="password"
                                class="form-control"
                                id="password"
                        />
                    </div>
                    <div class="form-group" id="ConfirmPasswordFormGroup">
                        <label for="confirmPassword">Confirm Password</label>
                        <input
                                v-model="confirmPasswordText"
                                type="password"
                                class="form-control"
                                id="confirmPassword"
                        />
                    </div>
                    <button class="btn btn-outline-primary btn-block" :disabled="loading">
                        <span v-show="loading" class="spinner-border spinner-border-sm"></span>
                        <span v-show="!loading">Register</span>
                    </button>
                    <div class="form-group">
                        <button class="btn btn-outline-danger btn-block" @click.prevent="$router.push('/login')">
                            Cancel
                        </button>
                    </div>
                </div>
            </form>

            <div
                    v-if="message"
                    class="alert"
                    :class="successful ? 'alert-success' : 'alert-danger'"
            >{{message}}
            </div>
        </div>
    </div>
</template>

<script>
    import User from '../models/user';
    import AuthService from "../services/AuthService";
    import Router from '../router';
    import {store} from "../store";

    export default {
        name: 'Register',
        data() {
            return {
                confirmPasswordText: '',
                user: new User('', '', ''),
                submitted: false,
                successful: false,
                message: '',
                loading: false
            };
        },
        computed: {},
        mounted() {
            return this.$store.getters.isAuthenticated;
        },
        methods: {
             async handleRegister() {
                this.loading = true;
                this.message = '';
                if (this.user.password !== this.confirmPasswordText) {
                    this.confirmPasswordText = '';
                    this.user.password = '';
                    alert("Passwords don't match, please try again.")
                    console.log(this.confirmPasswordText + '\n' + this.user.password);
                    return;
                }
                var tempUser = await AuthService.register(this.user)
                    .then(function (response) {
                        console.log('Done Registering');
                        return response;
                    })
                    .catch(function (error) {
                        console.log(error);
                        alert(error);
                    });
                console.log(tempUser);
                if(tempUser == undefined){
                    alert('TempUser undefined');
                    return;
                }
                var newUser = new User(this.user.username,'', this.user.password);
                console.log(newUser);
                await AuthService.login(newUser)
                    .then(function (response) {
                        store.commit("setUser", response);
                        store.commit("setAccess", response.accessLevel);
                        Router.push('/');
                    })
                    .catch(function (error) {
                        if (error.response.status == 401) {
                            alert("Unauthorized.  Username or Password may be bad.")
                        }if (error.response) {
                            console.log(error.response.data);
                            console.log(error.response.status);
                            console.log(error.response.headers);
                        }
                    });
                this.loading = false;
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