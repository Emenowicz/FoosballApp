<template>
    <v-layout>
        <v-navigation-drawer
                v-model="drawer"
                fixed
                app
        >
            <v-list dense>

            </v-list>
        </v-navigation-drawer>
        <v-toolbar color="green" dark fixed app>
            <v-toolbar-side-icon @click.stop="drawer = !drawer"></v-toolbar-side-icon>
            <v-toolbar-title>Foosball League</v-toolbar-title>
            <v-spacer></v-spacer>
            <v-btn flat @click="goToLogin">Zaloguj się</v-btn>
        </v-toolbar>
        <v-content>
            <v-container fluid fill-height>
                <v-layout
                        justify-center
                        align-center
                >
                </v-layout>
            </v-container>
        </v-content>
    </v-layout>


</template>

<script>
    export default {
        name: "Navbar",
        methods: {
            goToLogin: function () {
                this.$router.push('/login')
            }
        }

    }
</script>

<style scoped>

</style>