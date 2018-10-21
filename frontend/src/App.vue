<template>
    <v-app>
        <Navbar/>
        <router-view/>
        <Footer/>
    </v-app>
</template>

<script>
    import Navbar from './components/Navbar'
    import Footer from './components/Footer'

    export default {
        name: 'App',
        data: () => ({
            drawer: null
        }),
        components: {
            Navbar,
            Footer
        },
        props: {
            source: String
        },
        created: function () {
            this.$store.state.isLoggedIn = false
        }

    }
</script>
