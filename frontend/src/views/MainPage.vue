<template>
    <v-fade-transition>
        <v-container fluid fill-height grid-list-xl>
            <v-layout row wrap justify-center>
                <v-flex d-flex xs12 sm6 v-if="this.$store.getters.isAuthenticated">
                    <v-card>
                        <v-card-title primary class="title">Twoje turnieje</v-card-title>
                        <v-card-text fill-height>
                            <v-data-table class="mb-5" :headers="tournamentHeaders" :items="ownedTournaments">
                                <template slot="items" slot-scope="props">
                                    <td>{{props.item.name}}</td>
                                    <td>{{props.item.status}}</td>
                                    <td>{{moment(props.item.timeCreated).format('LT')}}
                                        {{moment(props.item.timeCreated).format('dddd')}}
                                        <br>
                                        {{moment(props.item.timeCreated).format('ll')}}
                                    </td>
                                </template>
                            </v-data-table>
                        </v-card-text>
                    </v-card>
                </v-flex>
                <v-flex d-flex xs12 sm6 v-if="this.$store.getters.isAuthenticated">
                    <v-card>
                        <v-card-title primary class="title">Turnieje w których bierzesz udział</v-card-title>
                        <v-card-text>
                            <v-data-table class="mb-5" :headers="tournamentHeaders" :items="joinedTournaments">
                                <template slot="items" slot-scope="props">
                                    <td>{{props.item.name}}</td>
                                    <td>{{props.item.status}}</td>
                                    <td>{{moment(props.item.timeCreated).format('LT')}}
                                        {{moment(props.item.timeCreated).format('dddd')}}
                                        <br>
                                        {{moment(props.item.timeCreated).format('ll')}}
                                    </td>
                                </template>
                            </v-data-table>
                        </v-card-text>
                    </v-card>
                </v-flex>
            </v-layout>
        </v-container>
    </v-fade-transition>
</template>

<script>
    import ApiConstants from "../libs/ApiConstants";
    import axios from 'axios';

    export default {
        name: "MainPage",
        data() {
            return {
                tournamentHeaders: [
                    {
                        text: 'Nazwa turnieju',
                        sortable: false,
                        value: 'name'
                    },
                    {
                        text: 'Status',
                        value: 'status'
                    },
                    {
                        text: 'Czas utworzenia',
                        value: 'timeCreated'
                    }
                ],
            }
        },
        computed: {
            ownedTournaments: function () {
                return this.$store.getters.getProfile.ownedTournaments
            },
            joinedTournaments: function (){
                return this.$store.getters.getProfile.joinedTournaments
            }
        },
        mounted: function () {
            this.getAllTournaments()
        },
        methods: {
            getAllTournaments() {
                axios(
                    {
                        url: ApiConstants.GET_ALL_TOURNAMENTS,
                        method: "GET"
                    }
                ).then(resp => {
                    this.allTournaments = resp.data
                })
            }
        }

    }
</script>

<style scoped>

</style>