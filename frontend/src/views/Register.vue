<template>
    <v-fade-transition>
        <v-container fill-height>
            <v-layout align-center justify-center>
                <v-flex xs12 sm8>
                    <v-alert class="my-4" :value="hasErrors" type="error" @click="closeAlert" transition="fade-transition">
                        Coś poszło nie
                        tak. Popraw dane
                        i spróbuj ponownie
                    </v-alert>
                    <h3 class="headline">Zarejestruj się</h3>
                    <v-form @submit.prevent="register">
                        <v-text-field prepend-icon="person" name="Username" label="Login"
                                v-model="username" :error-messages="usernameErrors" @blur="$v.username.$touch()"
                                @input="$v.username.$touch()"></v-text-field>
                        <v-text-field prepend-icon="lock" name="Password" label="Hasło" type="password"
                                v-model="password" :error-messages="passwordErrors" @blur="$v.password.$touch()"
                                @click="$v.password.$touch()"></v-text-field>
                        <v-text-field prepend-icon="repeat" name="ConfirmPassword" label="Potwierdź hasło"
                                type="password" v-model="confirmPassword" :error-messages="confirmPasswordErrors"
                                @blur="$v.confirmPassword.$touch()"
                                @input="$v.confirmPassword.$touch()"></v-text-field>
                        <v-text-field prepend-icon="mail" name="Email" label="E-mail" v-model="email"
                                :error-messages="emailErrors" @blur="$v.email.$touch()"
                                @input="$v.email.$touch()"></v-text-field>
                        <v-btn type="submit">Zarejestruj się</v-btn>
                        <v-btn v-on:click="redirectToLogin">Posiadam konto</v-btn>
                    </v-form>
                </v-flex>
            </v-layout>
        </v-container>
    </v-fade-transition>
</template>

<script>
    import axios from "axios";
    import ApiConstants from "../constants/ApiConstants";
    import required from "vuelidate/src/validators/required";
    import minLength from "vuelidate/src/validators/minLength";
    import email from "vuelidate/src/validators/email";
    import sameAs from "vuelidate/src/validators/sameAs";
    import maxLength from "vuelidate/src/validators/maxLength";

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
        validations: {
            username: {
                required,
                minLength: minLength(4),
                maxLength: maxLength(10)
            },
            email: {
                required,
                email
            },
            password: {
                required,
                minLength: minLength(5)
            },
            confirmPassword: {
                required,
                sameAsPassword: sameAs('password')
            },

        },
        computed: {
            hasErrors() {
                return !!this.errors.length
            },
            usernameErrors() {
                const errors = []
                if (!this.$v.username.$dirty) return errors
                !this.$v.username.minLength && errors.push('Login musi składać się z conajmniej 4 znaków')
                !this.$v.username.maxLength && errors.push('Login może składać się maksymalnie z 10 znaków')
                !this.$v.username.required && errors.push('Login jest wymagany')
                return errors
            },
            passwordErrors() {
                const errors = []
                if (!this.$v.password.$dirty) return errors
                !this.$v.password.minLength && errors.push('Hasło musi składać się z conajmniej 5 znaków')
                !this.$v.username.required && errors.push('Hasło jest wymagane')
                return errors
            },
            confirmPasswordErrors() {
                const errors = []
                if (!this.$v.confirmPassword.$dirty) return errors
                !this.$v.confirmPassword.sameAsPassword && errors.push('Hasła muszą się zgadzać')
                !this.$v.confirmPassword.required && errors.push('Powtórzenie hasła jest wymagane')
                return errors
            },
            emailErrors() {
                const errors = []
                if (!this.$v.email.$dirty) return errors
                !this.$v.email.email && errors.push('Zły format email')
                !this.$v.email.required && errors.push('Email jest wymagany')
                return errors
            }
        },
        methods: {
            register() {
                this.$v.$touch()
                if (!this.$v.$anyError) {
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
                }
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