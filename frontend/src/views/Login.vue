<template>
    <v-container fill-height>
        <v-layout align-center justify-center>
            <v-flex xs6>
                <h3 class="headline">Zaloguj się</h3>
                <v-form @submit.prevent="login">
                    <v-text-field prepend-icon="person" name="Username" label="Login"
                                  v-model="username"></v-text-field>
                    <v-text-field prepend-icon="lock" name="Password" label="Hasło" type="password"
                                  v-model="password"></v-text-field>

                    <v-btn type="submit">Zaloguj</v-btn>
                    <v-btn v-on:click="register">Zarejestruj się</v-btn>
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
                grant_type: 'password'
            }
        },
        methods: {
            login() {
                const {grant_type, username, password} = this
                this.$store.dispatch(AUTH_REQUEST, {grant_type, username, password})
                    .then(() => {
                        this.$router.push('/')
                    })
                console.log(this.username)
                console.log(this.password)
            },
            register() {
                this.$router.push('/register')
                console.log("register")
            }
        }

    }
</script>

<style scoped>

</style>