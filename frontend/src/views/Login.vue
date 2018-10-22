<template>
    <v-container fill-height>
        <v-layout align-center justify-center>

            <v-flex xs12 sm6>
                <v-alert class="my-4" :value="hasErrors" type="error" @click="closeAlert" transition="fade-transition">
                    Coś poszło nie
                    tak. Popraw dane
                    logowania i spróbuj ponownie
                </v-alert>
                <div class="headline text-xs-center">Zaloguj się</div>
                <v-form @submit.prevent="login">
                    <v-text-field prepend-icon="person" name="Username" label="Login"
                                  v-model="username"></v-text-field>
                    <v-text-field prepend-icon="lock" name="Password" label="Hasło" type="password"
                                  v-model="password"></v-text-field>
                    <v-layout>
                        <v-flex xs6>
                            <v-btn block type="submit">Zaloguj</v-btn>
                        </v-flex>
                        <v-flex xs6>
                            <v-btn block v-on:click="register">Zarejestruj się</v-btn>
                        </v-flex>
                    </v-layout>

                </v-form>
            </v-flex>
        </v-layout>
    </v-container>

</template>

<script>
    import {AUTH_REQUEST} from "../store/actions/authentication";

    export default {
        name: "Login",
        data() {
            return {
                username: '',
                password: '',
                grant_type: 'password',
                errors: []
            }
        },
        computed: {
            hasErrors() {
                return !!this.errors.length
            }
        },
        methods: {
            login() {
                this.errors = []
                const {grant_type, username, password} = this
                this.$store.dispatch(AUTH_REQUEST, {grant_type, username, password})
                    .then(() => {
                        this.$router.push('/')
                    }).catch(err => {
                    this.errors = [...this.errors, err]
                })
            },
            register() {
                this.$router.push('/register')
            },
            closeAlert() {
                this.errors = []
            }
        },
        validations: {
            username: {
                required
            },
            password: {
                required
            }
        }

    }
</script>

<style scoped>

</style>