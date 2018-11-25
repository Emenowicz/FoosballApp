<template>
    <v-fade-transition>
        <v-container fill-height>
            <v-layout align-center justify-center>
                <v-flex xs12 sm6>
                    <v-alert class="my-4" :value="hasErrors" type="error" @click="closeAlert"
                            transition="fade-transition">
                        {{errors[0]}}
                    </v-alert>
                    <div class="headline text-xs-center">Zaloguj się</div>
                    <v-form @submit.prevent="login">
                        <v-text-field prepend-icon="person" name="Username" label="Login"
                                v-model="username" :error-messages="usernameErrors" @input="$v.username.$touch()"
                                @blur="$v.username.$touch()"></v-text-field>
                        <v-text-field prepend-icon="lock" name="Password" label="Hasło" type="password"
                                v-model="password" :error-messages="passwordErrors" @input="$v.password.$touch()"
                                @blur="$v.password.$touch()"></v-text-field>
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
    </v-fade-transition>
</template>

<script>
    import {AUTH_REQUEST} from "../store/actions/authentication";
    import required from "vuelidate/src/validators/required";

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
            },
            usernameErrors() {
                const errors = []
                if (!this.$v.username.$dirty) return errors
                !this.$v.username.required && errors.push('Login jest wymagany')
                return errors
            },
            passwordErrors() {
                const errors = []
                if (!this.$v.password.$dirty) return errors
                !this.$v.password.required && errors.push('Hasło jest wymagane')
                return errors
            }
        },
        methods: {
            login() {
                this.$v.$touch()
                if (!this.$v.$anyError) {
                    this.errors = []
                    const {grant_type, username, password} = this
                    this.$store.dispatch(AUTH_REQUEST, {grant_type, username, password})
                        .then(() => {
                            this.$router.push('/')
                        }).catch(err => {
                        if (err.response.data.error_description === "Bad credentials") {
                            this.errors = [...this.errors, "Zły login lub hasło"]
                        } else {
                            this.errors = [...this.errors, "Coś poszło nie tak, spróbuj ponownie później"]
                        }
                    })
                }
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