<template>
    <v-container fluid fill-height grid-list-xl>
        <v-layout align-center justify-center>
            <v-flex d-flex fill-height>
                <v-card>
                    <v-card-title class="headline pb-0">
                        <p class="text-uppercase pb-0 mb-0">{{tournament.name}}</p>
                        <v-spacer />
                        <p class="subheading pb-0 mb-0">Status: &nbsp</p>
                        <p class="subheading pb-0 mb-0 green--text" v-if="tournament.status==='Otwarty'">{{tournament.status}}</p>
                        <p class="subheading pb-0 mb-0 orange--text"
                                v-if="tournament.status==='W trakcie'">{{tournament.status}}</p>
                        <p class="subheading pb-0 mb-0 red--text"
                                v-if="tournament.status==='Zakończony'">{{tournament.status}}</p>
                    </v-card-title>
                    <v-card-text class="pt-0">
                        <v-layout wrap align-center justify-space-around>
                            <v-flex xs12 sm8 class="subheading" style="overflow-wrap: break-word">{{tournament.description}}
                            </v-flex>
                            <v-flex>
                                <v-layout column align-center justify-center>
                                    <v-flex xs12 sm4 class="text-xs-center">Właściciel turnieju: {{tournament.owner.username}}
                                    </v-flex>
                                    <v-flex xs12 sm4 class="text-xs-center">
                                        Utworzony:
                                        <br>
                                        {{moment(tournament.timeCreated).format('LT')}}
                                        {{moment(tournament.timeCreated).format('dddd')}}
                                        <br>
                                        {{moment(tournament.timeCreated).format('ll')}}
                                    </v-flex>
                                </v-layout>
                            </v-flex>

                        </v-layout>
                        <v-layout wrap align-center justify-space-around>
                            <v-flex>
                                <v-text-field flat readonly label="Liczba graczy w drużynie"
                                        v-model="tournament.ruleSet.teamSize"></v-text-field>
                            </v-flex>
                            <v-flex>
                                <v-text-field flat readonly label="Punkty do zwycięstwa setu"
                                        v-model="tournament.ruleSet.pointsToWin"></v-text-field>
                            </v-flex>
                            <v-flex>
                                <v-text-field flat readonly label="Punkty do zwycięstwa meczu"
                                        v-model="tournament.ruleSet.roundsToWin"></v-text-field>
                            </v-flex>
                        </v-layout>
                        <v-divider></v-divider>
                        <v-layout class="mb-3" wrap>
                            <v-flex>
                                <v-card>
                                    <v-card-title class="subheading">
                                        Uczestnicy
                                    </v-card-title>
                                    <v-card-text>
                                        <v-data-iterator :items="tournament.participants" content-tag="v-layout" row wrap
                                                hide-actions>
                                            <v-flex slot="item" slot-scope="props" xs12 sm6 md4 lg3>
                                                <v-card>
                                                    <v-card-title><h4>{{props.item.username}}</h4></v-card-title>
                                                </v-card>
                                            </v-flex>
                                        </v-data-iterator>
                                    </v-card-text>
                                </v-card>
                            </v-flex>
                            <v-flex>
                                <v-card>
                                    <v-card-title class="subheading">
                                        Drużyny
                                    </v-card-title>
                                    <v-card-text>
                                        <v-data-iterator :items="tournament.teams" content-tag="v-layout" row wrap hide-actions>
                                            <v-flex slot="item" slot-scope="props" xs12 sm6 md4 lg3>
                                                <v-card v-if="props.item.size!==0">
                                                    <v-card-title><h4>{{props.item.name}}</h4></v-card-title>
                                                    <v-card-text>
                                                        <v-list>
                                                            <v-list-tile v-for="player in props.item.players"
                                                                    :key="player.username">
                                                                <v-list-tile-content>
                                                                    <v-list-tile-title
                                                                            v-text="player.username"></v-list-tile-title>
                                                                </v-list-tile-content>
                                                            </v-list-tile>
                                                        </v-list>
                                                    </v-card-text>
                                                </v-card>
                                            </v-flex>
                                        </v-data-iterator>
                                    </v-card-text>
                                </v-card>
                            </v-flex>
                        </v-layout>
                        <v-layout>
                            <v-speed-dial fixed right bottom class="floating-button-corner">
                                <v-btn slot="activator" color="blue darken-2" dark fab>
                                    <v-icon>account_circle</v-icon>
                                    <v-icon>close</v-icon>
                                </v-btn>
                                <v-btn fab dark small color="green" >
                                    <v-icon>edit</v-icon>
                                </v-btn>
                                <v-btn fab dark small color="indigo">
                                    <v-icon>add</v-icon>
                                </v-btn>
                                <v-btn fab dark small color="red">
                                    <v-icon>delete</v-icon>
                                </v-btn>
                            </v-speed-dial>
                        </v-layout>
                    </v-card-text>
                </v-card>
            </v-flex>
        </v-layout>
    </v-container>
</template>

<script>
    import axios from 'axios'
    import ApiConstants from "../constants/ApiConstants";

    export default {
        name: "TournamentPage",
        data() {
            return {
                tournament: '',
                errors: []
            }
        },
        mounted() {
            this.loadTournament()
        },
        methods: {
            loadTournament() {
                axios({
                    url: ApiConstants.GET_TOURNAMENT + this.$route.params.id,
                    method: "GET"
                }).then(resp => {
                    this.tournament = resp.data
                }).catch(err => {
                    this.errors.push(...this.errors + err)
                })
            }
        }
    }
</script>

<style scoped>
    p {
        overflow-wrap: break-word;
    }
    .floating-button-corner {
        bottom: 10%;
        right: 5%;
    }


</style>