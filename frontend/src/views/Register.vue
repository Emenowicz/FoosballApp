<template>
    <v-container fill-height>
        <v-layout align-center justify-center>
            <v-flex xs6>
                <v-alert class="my-4" :value="hasErrors" type="error" @click="closeAlert" transition="fade-transition">
                    Coś poszło nie
                    tak. Popraw dane
                    i spróbuj ponownie
                </v-alert>
                <h3 class="headline">Zarejestruj się</h3>
                <v-form @submit.prevent="register">
                    <v-text-field prepend-icon="person" name="Username" label="Login"
                                  v-model="username"></v-text-field>
                    <v-text-field prepend-icon="lock" name="Password" label="Hasło" type="password"
                                  v-model="password"></v-text-field>
                    <v-text-field prepend-icon="repeat" name="ConfirmPassword" label="Potwierdź hasło"
                                  type="password" v-model="confirmPassword"></v-text-field>
                    <v-text-field prepend-icon="mail" name="Email" label="E-mail" v-model="email"></v-text-field>
                    <v-btn type="submit">Zarejestruj się</v-btn>
                    <v-btn v-on:click="redirectToLogin">Posiadam konto</v-btn>
                </v-form>
            </v-flex>
        </v-layout>
    </v-container>
</template>

<script>
    import axios from "axios";
    import ApiConstants from "../libs/ApiConstants";

    export default {
        name: "Register",
        data() {
            return {
                username: '',
                password: '',
                email: '',
                confirmPassword: '',
                errors: []
            }
        },
        computed: {
            hasErrors() {
                return !!this.errors.length
            }
        },
        methods: {
            register() {
                this.errors = [];
                axios(
                    {
                        url: ApiConstants.REGISTER_ENDPOINT,
                        data: {
                            'username': this.username,
                            'password': this.password,
                            'email': this.email
                        },
                        method: 'POST'
                    }
                ).then(() => {
                    this.$router.push('/')
                }).catch(err => {
                    this.errors = [...this.errors, err]
                })
            },
            redirectToLogin() {
                this.$router.push('/login')
            },
            closeAlert() {
                this.errors = []
            }
        }
    }
</script>

<style scoped>

</style>