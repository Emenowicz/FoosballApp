<template>
    <v-fade-transition>
        <v-container>
            <v-layout align-center justify-center>
                <v-flex xs6 text-xs-center>
                    <div class="my-4 headline">Zarządzanie kontem</div>
                    <v-alert class="my-0" :value="hasErrors" type="error" @click="closeAlert"
                             transition="fade-transition">
                        {{errors[0]}}
                    </v-alert>
                    <v-alert class="my-0" :value="hasInfos" type="success" @click="closeInfos"
                             transition="fade-transition">
                        {{infos[0]}}
                    </v-alert>
                    <v-tabs v-model="activeTab" color="green" dark grow>
                        <v-tab ripple>
                            Twoje dane
                        </v-tab>
                        <v-tab-item>
                            <v-form @submit.prevent="saveUserDetails">
                                <v-text-field readonly prepend-icon="person" name="Username" label="Login" v-model="username"
                                              :error-messages="usernameErrors" @input="$v.username.$touch()"
                                              @blur="$v.username.$touch()"></v-text-field>
                                <v-text-field prepend-icon="mail" name="Email" label="E-mail" v-model="email"
                                              :error-messages="emailErrors" @blur="$v.email.$touch()"
                                              @input="$v.email.$touch()"></v-text-field>
                                <v-btn type="submit">Zapisz zmiany</v-btn>

                            </v-form>
                        </v-tab-item>

                        <v-tab>
                            Zmień hasło
                        </v-tab>
                        <v-tab-item>
                            <v-form @submit.prevent="updatePassword">
                                <v-text-field prepend-icon="lock" name="Password" label="Aktualne hasło" type="password"
                                              v-model="currentPassword" :error-messages="currentPasswordErrors"
                                              @blur="$v.currentPassword.$touch()"
                                              @click="$v.currentPassword.$touch()"></v-text-field>
                                <v-text-field prepend-icon="lock" name="Password" label="Nowe hasło" type="password"
                                              v-model="newPassword" :error-messages="newPasswordErrors"
                                              @blur="$v.newPassword.$touch()"
                                              @click="$v.newPassword.$touch()"></v-text-field>
                                <v-text-field prepend-icon="repeat" name="ConfirmPassword" label="Potwierdź hasło"
                                              type="password" v-model="confirmPassword"
                                              :error-messages="confirmPasswordErrors"
                                              @blur="$v.confirmPassword.$touch()"
                                              @input="$v.confirmPassword.$touch()"></v-text-field>
                                <v-btn type="submit">Zapisz zmiany</v-btn>
                            </v-form>
                        </v-tab-item>
                        <v-btn flat icon v-on:click="loadUserData"><v-icon>replay</v-icon></v-btn>
                    </v-tabs>
                </v-flex>
            </v-layout>
        </v-container>

    </v-fade-transition>

</template>

<script>

    import axios from 'axios'
    import required from "vuelidate/src/validators/required";
    import minLength from "vuelidate/src/validators/minLength";
    import maxLength from "vuelidate/src/validators/maxLength";
    import email from "vuelidate/src/validators/email";
    import sameAs from "vuelidate/src/validators/sameAs";
    import ApiConstants from "../constants/ApiConstants";
    import {USER_REQUEST} from "../store/actions/user";

    export default {
        name: "MyAccount",
        data() {
            return {
                username: '',
                email: '',
                currentPassword: '',
                newPassword: '',
                confirmPassword: '',
                activeTab: null,
                errors: [],
                infos: []
            }
        },
        mounted() {
            this.loadUserData()
        },
        methods: {
            loadUserData() {
                this.username = this.userProfile.username
                this.email = this.userProfile.email
            },
            saveUserDetails() {
                this.$v.username.$touch()
                this.$v.email.$touch()
                if (!this.$v.username.$anyError&&!this.$v.email.$anyError) {
                    this.errors = [];

                    axios(
                        {
                            url: ApiConstants.UPDATE_USER_DETAILS,
                            data: {
                                'username': this.username,
                                'email': this.email
                            },
                            method: 'POST'
                        }
                    ).then(() => {
                        this.infos = [...this.infos, "Zaktualizowano poprawnie"]
                        this.$store.dispatch(USER_REQUEST)
                    }).catch(err => {
                        this.errors = [...this.errors, err.response.data]
                    })
                }
            },
            updatePassword(){
                this.$v.currentPassword.$touch()
                this.$v.newPassword.$touch()
                this.$v.confirmPassword.$touch()
                if(!this.$v.currentPassword.$anyError && !this.$v.newPassword.$anyError && !this.$v.confirmPassword.$anyError){
                    axios(
                        {
                            url: ApiConstants.UPDATE_USER_PASSWORD,
                            data: {
                                'currentPassword': this.currentPassword,
                                'newPassword': this.newPassword
                            },
                            method: 'POST'
                        }
                    ).then(() => {
                        this.infos = [...this.infos, "Zaktualizowano poprawnie"]
                        this.$store.dispatch(USER_REQUEST)
                    }).catch(err => {
                        this.errors = [...this.errors, err.response.data]
                    })
                }
            },
            closeAlert() {
                this.errors = []
            },
            closeInfos() {
                this.infos = []
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
            currentPassword: {
                required,
                minLength: minLength(5)
            },
            newPassword: {
                required,
                minLength: minLength(5)
            },
            confirmPassword: {
                required,
                sameAsPassword: sameAs('newPassword')
            },
        },
        computed: {
            userProfile(){
                return this.$store.getters.getProfile
            },
            hasErrors() {
                return !!this.errors.length
            },
            hasInfos(){
                return !!this.infos.length
            },
            usernameErrors() {
                const errors = []
                if (!this.$v.username.$dirty) return errors
                !this.$v.username.minLength && errors.push('Login musi składać się z conajmniej 4 znaków')
                !this.$v.username.maxLength && errors.push('Login może składać się maksymalnie z 10 znaków')
                !this.$v.username.required && errors.push('Login jest wymagany')
                return errors
            },
            currentPasswordErrors() {
                const errors = []
                if (!this.$v.currentPassword.$dirty) return errors
                !this.$v.currentPassword.minLength && errors.push('Hasło musi składać się z conajmniej 5 znaków')
                !this.$v.currentPassword.required && errors.push('Hasło jest wymagane')
                return errors
            },
            newPasswordErrors() {
                const errors = []
                if (!this.$v.newPassword.$dirty) return errors
                !this.$v.newPassword.minLength && errors.push('Hasło musi składać się z conajmniej 5 znaków')
                !this.$v.newPassword.required && errors.push('Hasło jest wymagane')
                return errors
            },
            confirmPasswordErrors() {
                const errors = []
                if (!this.$v.confirmPassword.$dirty) return errors
                !this.$v.confirmPassword.required && errors.push('Powtórzenie hasła jest wymagane')
                !this.$v.confirmPassword.sameAsPassword && errors.push('Hasła muszą się zgadzać')
                return errors
            },
            emailErrors() {
                const errors = []
                if (!this.$v.email.$dirty) return errors
                !this.$v.email.email && errors.push('Zły format email')
                !this.$v.email.required && errors.push('Email jest wymagany')
                return errors
            }
        }
    }
</script>

<style scoped>

</style>