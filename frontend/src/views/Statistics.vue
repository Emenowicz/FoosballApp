<template>
    <v-fade-transition>
        <v-container fluid fill-height grid-list-xl>
            <v-layout row align-center justify-center>
                <v-flex d-flex fill-height>
                    <v-card>
                        <v-card-title class="headline">
                            Twoje statystyki
                        </v-card-title>
                        <v-card-text>
                            <v-flex d-flex>
                                <v-card>
                                    <v-card-title>
                                        <h5 class="headline">Statystyki</h5>
                                    </v-card-title>
                                    <v-card-text>
                                        <v-layout v-if="statisticsLoaded" wrap align-center justify-space-around>
                                            <v-flex>
                                                <v-text-field flat readonly label="Wygrane turnieje"
                                                        v-model="statistics.tournamentsWon">
                                                </v-text-field>
                                            </v-flex>
                                            <v-flex>
                                                <v-text-field flat readonly label="Rozegrane turnieje"
                                                        v-model="statistics.tournamentsPlayed"></v-text-field>
                                            </v-flex>
                                            <v-flex>
                                                <v-text-field flat readonly label="Wygrane mecze"
                                                        v-model="statistics.matchesWon"></v-text-field>
                                            </v-flex>
                                            <v-flex>
                                                <v-text-field flat readonly label="Rozegrane mecze"
                                                        v-model="statistics.matchesPlayed"></v-text-field>
                                            </v-flex>
                                            <v-flex>
                                                <v-text-field flat readonly label="Wygrane rundy"
                                                        v-model="statistics.roundsWon">
                                                </v-text-field>
                                            </v-flex>
                                            <v-flex>
                                                <v-text-field flat readonly label="Rozegrane rundy"
                                                        v-model="statistics.roundsPlayed"></v-text-field>
                                            </v-flex>
                                            <v-flex>
                                                <v-text-field flat readonly label="Suma zdobytych punktów"
                                                        v-model="statistics.pointsScored"></v-text-field>
                                            </v-flex>
                                            <v-flex>
                                                <v-text-field flat readonly label="Procent wygranych meczy"
                                                        v-model="statistics.matchWinRatio" suffix="%"></v-text-field>
                                            </v-flex>
                                            <v-flex>
                                                <v-text-field flat readonly label="Ulubiony teammate"
                                                        v-model="statistics.mostPlayedWith">
                                                </v-text-field>
                                            </v-flex>
                                        </v-layout>
                                    </v-card-text>
                                </v-card>
                            </v-flex>
                            <v-flex d-flex>
                                <v-card class="hidden-xs-only">
                                    <v-card-title>
                                        <h5 class="headline">Historia meczów</h5>
                                    </v-card-title>
                                    <v-card-text>
                                        <v-timeline v-if="lastMatches.length!==0" align-top>
                                            <v-timeline-item v-for="(match,index) in lastMatches" :key="index"
                                                    :icon="calculateIcon(match)" :color="calculateColor(match)" fill-dot>
                                                <span slot="opposite" :class="'headline font-weight-bold grey--text'"
                                                        v-text="match.tournament.name"></span>
                                                <v-card :color="calculateColor(match)" dark>
                                                    <v-card-title
                                                            class="title text-xs-center">{{match.teamOne.name}} vs {{match.teamTwo.name}}
                                                    </v-card-title>
                                                    <v-card-text class="white text--primary text-xs-center">
                                                        <h2 class="display-1">{{match.scoreOne}} - {{match.scoreTwo}}</h2>
                                                    </v-card-text>
                                                </v-card>
                                            </v-timeline-item>
                                        </v-timeline>
                                        <p v-else class="text-xs-center subheading">
                                            Rozegraj najpierw kilka meczy, a potem wróć tutaj ;)
                                        </p>
                                    </v-card-text>
                                </v-card>
                                <v-card class="hidden-sm-and-up">
                                    <v-card-title>
                                        <h5 class="headline">Historia meczów</h5>
                                    </v-card-title>
                                    <v-card-text>
                                        <v-timeline v-if="lastMatches.length!==0" align-top dense>
                                            <v-timeline-item v-for="(match,index) in lastMatches" :key="index"
                                                    :icon="calculateIcon(match)" :color="calculateColor(match)" fill-dot>
                                                <span slot="opposite" :class="'headline font-weight-bold grey--text'"
                                                        v-text="match.tournament.name"></span>
                                                <v-card :color="calculateColor(match)" dark>
                                                    <v-card-title
                                                            class="title text-xs-center">{{match.teamOne.name}} vs {{match.teamTwo.name}}
                                                    </v-card-title>
                                                    <v-card-text class="white text--primary text-xs-center">
                                                        <h2 class="display-1">{{match.scoreOne}} - {{match.scoreTwo}}</h2>
                                                    </v-card-text>
                                                </v-card>
                                            </v-timeline-item>
                                        </v-timeline>
                                        <p v-else class="text-xs-center subheading">
                                            Rozegraj najpierw kilka meczy, a potem wróć tutaj ;)
                                        </p>
                                    </v-card-text>
                                </v-card>
                            </v-flex>
                        </v-card-text>
                    </v-card>
                </v-flex>
            </v-layout>
        </v-container>
    </v-fade-transition>
</template>

<script>
    import ApiConstants from "../constants/ApiConstants";
    import axios from 'axios';

    export default {
        name: "Statistics",
        data() {
            return {
                statistics: {
                    tournamentsWon: '',
                    tournamentsPlayed: '',
                    matchesWon: '',
                    matchesPlayed: '',
                    roundsWon: '',
                    roundsPlayed: '',
                    pointsScored: '',
                    matchWinRatio: '',
                    mostPlayedWith: '',
                },
                lastMatches: [],
                errors: [],
                statisticsLoaded: false,
            }
        },
        methods: {
            loadStatistics() {
                axios({
                    url: ApiConstants.GET_STATISTICS,
                    method: "GET"
                }).then(resp => {
                    this.statistics = resp.data
                }).catch(err => {
                    this.errors = [...this.errors, err.response.data]
                })
            },
            loadMatches() {
                axios({
                    url: ApiConstants.GET_MATCH_HISTORY,
                    method: "GET"
                }).then(resp => {
                    this.lastMatches = resp.data
                    this.statisticsLoaded = true
                }).catch(err => {
                    this.errors = [...this.errors, err.response.data]
                    this.statisticsLoaded = false
                });
            },
            calculateColor(match) {
                return this.isWon(match) ? "green" : "red";
            },
            calculateIcon(match) {
                return this.isWon(match) ? "sentiment_very_satisfied" : "sentiment_very_dissatisfied";
            },
            isWon(match) {
                let isWon = false;
                match.winner.players.forEach(player => {
                    if (player.id === this.$store.getters.getProfile.id) {
                        isWon = true;
                    }
                });
                return isWon;
            }
        },
        mounted() {
            this.loadStatistics();
            this.loadMatches();
        },

    }
</script>

<style scoped>

</style>