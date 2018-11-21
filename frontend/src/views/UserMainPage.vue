<template>
    <v-fade-transition>
        <v-container fluid fill-height grid-list-xl>
            <v-layout column>
                <v-flex>
                    <v-layout row wrap justify-center>
                        <v-flex xs12 sm6 d-flex>
                            <v-card>
                                <v-card-title primary class="title">Twoje oczekujące mecze</v-card-title>
                                <v-card-text>
                                    <v-list>
                                        <v-list-tile @click="setScoreDialog(match)" v-for="match in awaitingMatches"
                                                :key="match.id">
                                            <v-list-tile-content>
                                                <v-list-tile-title v-if="!!match.teamTwo"
                                                        class="green--text">{{match.teamOne.name}} vs {{match.teamTwo.name}}
                                                </v-list-tile-title>
                                                <v-list-tile-title v-else
                                                        class="orange--text">{{match.teamOne.name}} vs Oczekiwanie na drużynę
                                                </v-list-tile-title>
                                                <v-list-tile-sub-title>{{match.tournament.name}}</v-list-tile-sub-title>
                                            </v-list-tile-content>
                                        </v-list-tile>
                                    </v-list>
                                    <v-dialog v-if="scoreDialog" v-model="scoreDialog" max-width="400">
                                        <v-card>
                                            <v-card-title class="headline text-xs-center">Podaj wyniki meczu</v-card-title>
                                            <v-alert ref="alert" class="my-4" :value="hasErrors" type="error" @click="closeAlerts"
                                                    transition="fade-transition">
                                                {{errors[0]}}
                                            </v-alert>
                                            <p class="subheading font-weight-bold text-xs-center">{{matchToSetScore.teamOne.name}} vs {{matchToSetScore.teamTwo.name}}</p>
                                            <p class="subheading text-xs-center">Rundy do zwycięstwa meczu:
                                                <b>{{matchToSetScore.tournament.ruleSet.roundsToWin}}</b></p>
                                            <p class="subheading text-xs-center">Punkty do zwycięstwa rundy:
                                                <b>{{matchToSetScore.tournament.ruleSet.pointsToWin}}</b></p>
                                            <v-divider></v-divider>
                                            <v-card-text>
                                                <v-form @submit.prevent="saveScore">
                                                    <v-flex v-for="(round,index) in matchToSetScore.rounds" :key="index">
                                                        <v-flex row class="text-xs-center">
                                                            <p class="subheading">Runda {{index+1}} </p>
                                                            <v-btn @click="matchToSetScore.rounds.splice(index,1)" absolute right
                                                                    icon>
                                                                <v-icon>delete</v-icon>
                                                            </v-btn>
                                                        </v-flex>
                                                        <v-flex>
                                                            <v-layout justify-center>
                                                                <v-flex xs2>
                                                                    <v-text-field v-model="round.scoreTeamOne"
                                                                            mask="##"
                                                                            :rules="[()=>!!round.scoreTeamOne || 'Wpisz wynik']"></v-text-field>
                                                                </v-flex>
                                                                <v-flex xs2 class="text-xs-center">
                                                                    <p class="headline"> - </p>
                                                                </v-flex>
                                                                <v-flex xs2>
                                                                    <v-text-field v-model="round.scoreTeamTwo"
                                                                            mask="##"
                                                                            :rules="[()=>!!round.scoreTeamTwo || 'Wpisz wynik']"></v-text-field>
                                                                </v-flex>
                                                            </v-layout>
                                                        </v-flex>
                                                        <v-divider></v-divider>
                                                    </v-flex>
                                                    <v-layout justify-center>
                                                        <v-btn v-if="!isEnoughRounds" @click="addRound">Dodaj rundę</v-btn>
                                                        <v-btn type="submit">Zapisz</v-btn>
                                                    </v-layout>
                                                </v-form>
                                            </v-card-text>
                                        </v-card>
                                    </v-dialog>
                                </v-card-text>
                            </v-card>
                        </v-flex>
                        <v-flex xs12 sm6 d-flex>
                            <v-card>
                                <v-card-title primary class="title">Twoje ostatnie mecze</v-card-title>
                                <v-card-text>
                                    <v-list>
                                        <v-list-tile v-for="match in lastMatches"
                                                :key="match.id">
                                            <v-list-tile-content>
                                                <v-list-tile-title class="blue-grey--text">
                                                    {{match.teamOne.name}} ({{match.scoreOne}} - {{match.scoreTwo}}) {{match.teamTwo.name}}
                                                </v-list-tile-title>
                                                <v-list-tile-sub-title>{{match.tournament.name}}</v-list-tile-sub-title>
                                            </v-list-tile-content>
                                        </v-list-tile>
                                    </v-list>
                                </v-card-text>
                            </v-card>
                        </v-flex>
                    </v-layout>
                </v-flex>
                <v-flex>
                    <v-layout row wrap justify-center>
                        <v-flex d-flex xs12 sm6>
                            <v-card>
                                <v-card-title primary class="title">Twoje turnieje
                                    <v-spacer>
                                    </v-spacer>
                                    <v-text-field
                                            v-model="ownedSearch"
                                            append-icon="search"
                                            label="Search"
                                            single-line
                                            hide-details
                                    ></v-text-field>
                                </v-card-title>
                                <v-card-text fill-height>
                                    <v-data-table class="mb-5" :headers="tournamentHeaders" :items="ownedTournaments"
                                            :search="ownedSearch" :pagination.sync="pagination">
                                        <template slot="items" slot-scope="props">
                                            <tr @click="props.expanded = !props.expanded">
                                                <td>{{props.item.name}}</td>
                                                <td>{{props.item.status}}</td>
                                                <td>{{props.item.ruleSet.pointsToWin}}</td>
                                                <td>{{props.item.ruleSet.roundsToWin}}</td>
                                                <td>{{props.item.ruleSet.teamSize}}</td>
                                                <td>{{moment(props.item.timeCreated).format('LT')}}
                                                    {{moment(props.item.timeCreated).format('dddd')}}
                                                    <br>
                                                    {{moment(props.item.timeCreated).format('ll')}}
                                                </td>
                                            </tr>
                                        </template>
                                        <template slot="expand" slot-scope="props">
                                            <v-flex>
                                                <v-card flat>
                                                    <v-card-text>
                                                        <p v-if="props.item.description">Opis: {{props.item.description}}</p>
                                                        <v-btn @click="openTournamentPage(props.item.id)">Zobacz turniej</v-btn>
                                                        <v-btn @click="startTournament(props.item.id)"
                                                                v-if="props.item.status==='Otwarty' && isTournamentFull(props.item.id)">Rozpocznij
                                                        </v-btn>
                                                        <v-btn @click="deleteDialog=true"
                                                                v-if="props.item.status==='Otwarty'">Usuń
                                                        </v-btn>
                                                        <v-dialog v-model="deleteDialog" max-width="300">
                                                            <v-card>
                                                                <v-card-title
                                                                        class="headline">Jesteś pewien że chcesz usunąć turniej
                                                                    <b>{{props.item.name}}</b>?
                                                                </v-card-title>
                                                                <v-card-text>
                                                                    <v-layout justify-center>
                                                                        <v-btn @click="deleteDialog=false">Anuluj</v-btn>
                                                                        <v-btn @click="deleteTournament(props.item.id)">Usuń</v-btn>
                                                                    </v-layout>
                                                                </v-card-text>
                                                            </v-card>
                                                        </v-dialog>
                                                    </v-card-text>
                                                </v-card>
                                            </v-flex>
                                        </template>
                                    </v-data-table>
                                </v-card-text>
                            </v-card>
                        </v-flex>
                        <v-flex d-flex xs12 sm6>
                            <v-card>
                                <v-card-title primary class="title">Turnieje w których bierzesz udział
                                    <v-spacer>
                                    </v-spacer>
                                    <v-text-field
                                            v-model="joinedSearch"
                                            append-icon="search"
                                            label="Search"
                                            single-line
                                            hide-details
                                    ></v-text-field>
                                </v-card-title>
                                <v-card-text>
                                    <v-data-table class="mb-5" :headers="tournamentHeaders" :items="joinedTournaments"
                                            :search="joinedSearch" :pagination.sync="pagination">
                                        <template slot="items" slot-scope="props">
                                            <tr @click="openTournamentPage(props.item.id)">
                                                <td>{{props.item.name}}</td>
                                                <td>{{props.item.status}}</td>
                                                <td>{{props.item.ruleSet.pointsToWin}}</td>
                                                <td>{{props.item.ruleSet.roundsToWin}}</td>
                                                <td>{{props.item.ruleSet.teamSize}}</td>
                                                <td>{{moment(props.item.timeCreated).format('LT')}}
                                                    {{moment(props.item.timeCreated).format('dddd')}}
                                                    <br>
                                                    {{moment(props.item.timeCreated).format('ll')}}
                                                </td>
                                            </tr>
                                        </template>
                                    </v-data-table>
                                </v-card-text>
                            </v-card>
                        </v-flex>
                    </v-layout>
                </v-flex>
            </v-layout>
        </v-container>
    </v-fade-transition>
</template>

<script>

    import ApiConstants from "../constants/ApiConstants";
    import axios from 'axios';
    import {USER_REQUEST} from "../store/actions/user";

    export default {
        name: "UserMainPage",
        data() {
            return {
                awaitingMatches: [],
                lastMatches: [],
                scoreDialog: false,
                deleteDialog: false,
                matchToSetScore: {},
                tournamentHeaders: [
                    {
                        text: 'Nazwa turnieju',
                        value: 'name'
                    },
                    {
                        text: 'Status',
                        value: 'status'
                    },
                    {
                        text: 'Punkty do zwycięstwa',
                        value: 'ruleSet.pointsToWin'
                    },
                    {
                        text: 'Rundy do zwycięstwa',
                        value: 'ruleSet.pointsToWin'
                    },
                    {
                        text: 'Wielkość drużyny',
                        value: 'ruleSet.teamSize'
                    },
                    {
                        text: 'Czas utworzenia',
                        value: 'timeCreated'
                    },
                ],
                pagination: {
                  'sortBy':'timeCreated',
                    'descending': true
                },
                errors: [],
                joinedSearch: '',
                ownedSearch: ''
            }
        },
        computed: {
            ownedTournaments: function () {
                return this.$store.getters.getProfile.ownedTournaments
            },
            joinedTournaments: function () {
                return this.$store.getters.getProfile.joinedTournaments
            },
            isEnoughRounds: function () {
                return this.matchToSetScore.rounds.length >= this.matchToSetScore.tournament.ruleSet.roundsToWin * 2 - 1;
            },
            hasErrors() {
                return !!this.errors.length
            },
        },
        methods: {
            loadData() {
                axios({
                    url: ApiConstants.GET_AWAITING_MATCHES,
                    method: "GET"
                }).then(resp => {
                    this.awaitingMatches = resp.data
                }).catch(err => {
                    this.errors = [...this.errors, err.response.data]
                });
                axios({
                    url: ApiConstants.GET_LAST_MATCHES,
                    method: "GET"
                }).then(resp => {
                    this.lastMatches = resp.data
                }).catch(err => {
                    this.errors = [...this.errors, err.response.data]
                });
                this.$store.dispatch(USER_REQUEST)

            },
            openTournamentPage(tournamentId) {
                this.$router.push({path: "/tournament/" + tournamentId})
            },
            startTournament(tournamentId) {
                axios({
                    url: ApiConstants.START_TOURNAMENT(tournamentId),
                    method: "POST"
                }).then(() => {
                    this.loadData()
                }).catch(err => {
                    this.errors = [...this.errors, err.response.data]
                })
            },
            isTournamentFull(tournamentId) {
                let tournaments = this.ownedTournaments.filter(tournament => {
                    return tournament.id === tournamentId;
                });
                return tournaments[0].teams.length === tournaments[0].teamsNeeded
            },
            setScoreDialog(match) {
                if (match.teamTwo) {
                    this.scoreDialog = true;
                    this.matchToSetScore = match
                }
            },
            addRound() {
                this.matchToSetScore.rounds = [...this.matchToSetScore.rounds, {
                    scoreTeamOne: '',
                    scoreTeamTwo: '',
                    pointsToWin: this.matchToSetScore.tournament.ruleSet.pointsToWin
                }]
            },
            saveScore() {
                axios({
                    url: ApiConstants.SAVE_SCORE(this.matchToSetScore.id),
                    data: {
                        rounds: this.matchToSetScore.rounds,
                    },
                    method: "POST"
                }).then(() => {
                    this.loadData()
                    this.scoreDialog = false
                }).catch(err => {
                    this.errors = [...this.errors, err.response.data]
                })
            },
            deleteTournament(tournamentId) {
                axios({
                    url: ApiConstants.TOURNAMENT(tournamentId),
                    method: "DELETE"
                }).then(() => {
                    this.loadData()
                }).catch(err => {
                    this.errors = [...this.errors, err.response.data]
                })
            },
            closeAlerts() {
                this.errors = []
            }
        },
        mounted() {
            this.loadData()
        }
    }
</script>

<style scoped>

</style>