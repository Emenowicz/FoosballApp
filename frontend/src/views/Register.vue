<template>
    <v-container fill-height>
        <v-layout align-center justify-center>
            <v-flex xs6>
                <h3 class="headline">Zarejestruj się</h3>
                <v-form @submit.prevent="register">
                    <v-text-field prepend-icon="person" name="Username" label="Login"
                                  v-model="username"></v-text-field>
                    <v-text-field prepend-icon="lock" name="Password" label="Hasło" type="password"
                                  v-model="password"></v-text-field>
                    <v-text-field prepend-icon="repeat" name="ConfirmPassword" label="Potwierdź hasło"
                                  type="password"></v-text-field>
                    <v-text-field prepend-icon="mail" name="Email" label="E-mail"></v-text-field>
                    <v-btn type="submit">Zarejestruj się</v-btn>
                    <v-btn v-on:click="redirectToLogin">Posiadam konto</v-btn>
                </v-form>
            </v-flex>
        </v-layout>
    </v-container>
</template>

<script>
    export default {
        name: "Register",
        data() {
            return {
                username: '',
                password: '',
                email: ''
            }
        },
        methods: {
            register(){
                console.log('register: ')
            },
            redirectToLogin(){
                this.$router.push('/login')
            }
        }
    }
</script>

<style scoped>

</style>