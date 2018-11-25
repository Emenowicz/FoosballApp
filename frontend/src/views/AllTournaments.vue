<template>
    <v-fade-transition>
        <v-container fluid fill-height grid-list-xl>
            <v-layout row align-center justify-center>
                <v-flex d-flex fill-height>
                    <v-card>
                        <v-card-title class="headline">
                            Wszystkie turnieje
                            <v-spacer></v-spacer>
                            <v-text-field
                                    v-model="search"
                                    append-icon="search"
                                    label="Search"
                                    single-line
                                    hide-details
                            ></v-text-field>
                        </v-card-title>
                        <v-card-text>
                            <v-data-iterator :items="tournaments" :rows-per-page-items="rowsPerPageItems"
                                    :pagination.sync="pagination" content-tag="v-layout" no-data-text="Brak turniejów"
                                    rows-per-page-text="Liczba turniejów na stronę"
                                    :search="search" row
                                    wrap>
                                <v-flex @click="openTournament(props.item.id)" slot="item" slot-scope="props" xs12 sm6 md4
                                        d-flex>
                                    <v-card ripple v-if="props.item.size!==0">
                                        <v-card-title class="subheading font-weight-bold pb-0">
                                            <v-layout justify-space-between>
                                                <v-flex>
                                                    <p class="text-uppercase pb-0 mb-0">{{props.item.name}}
                                                        <v-icon small color="green"
                                                                v-if="isParticipant(props.item)">lens
                                                        </v-icon></p>
                                                </v-flex>
                                                <v-flex no-wrap class="text-xs-right">
                                                    <p class="subheading pb-0 mb-0">Status:&nbsp;</p>
                                                    <p class="subheading pb-0 mb-0 green--text"
                                                            v-if="props.item.status==='Otwarty'">{{props.item.status}}</p>
                                                    <p class="subheading pb-0 mb-0 orange--text"
                                                            v-if="props.item.status==='Trwający'">{{props.item.status}}</p>
                                                    <p class="subheading pb-0 mb-0 red--text"
                                                            v-if="props.item.status==='Zakończony'">{{props.item.status}}</p>
                                                </v-flex>
                                            </v-layout>
                                        </v-card-title>
                                        <v-card-text class="pt-1">
                                            <v-subheader v-if="!!props.item.description">{{props.item.description}}</v-subheader>
                                            <v-subheader>Aktualna liczba graczy: {{props.item.participants.length}}/{{props.item.teamsNeeded*props.item.ruleSet.teamSize}}</v-subheader>
                                            <v-subheader>Zestaw zasad: {{props.item.ruleSet.name}}</v-subheader>
                                            <v-layout column align-center justify-start>
                                                <v-flex d-flex>
                                                    <v-text-field flat readonly label="Liczba graczy w drużynie"
                                                            v-model="props.item.ruleSet.teamSize"></v-text-field>
                                                </v-flex>
                                                <v-flex d-flex>
                                                    <v-text-field flat readonly label="Punkty do zwycięstwa"
                                                            v-model="props.item.ruleSet.pointsToWin"></v-text-field>
                                                </v-flex>
                                                <v-flex d-flex>
                                                    <v-text-field flat readonly label="Rundy do zwycięstwa"
                                                            v-model="props.item.ruleSet.roundsToWin"></v-text-field>
                                                </v-flex>
                                            </v-layout>
                                        </v-card-text>
                                    </v-card>
                                </v-flex>
                                <template slot="pageText" slot-scope="props">
                                    {{props.pageStart}}-{{props.pageStop}} na {{props.itemsLength}}
                                </template>
                            </v-data-iterator>
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
    import {USER_REQUEST} from "../store/actions/user";

    export default {
        name: "AllTournaments",
        data() {
            return {
                tournaments: [],
                rowsPerPageItems: [6, 9, 12],
                pagination: {'rowsPerPage': 6},
                search: ''
            }
        },
        methods: {
            loadData() {
                axios({
                    url: ApiConstants.GET_ALL_TOURNAMENTS,
                    method: "GET"
                }).then(resp => {
                    this.$store.dispatch(USER_REQUEST)
                    this.tournaments = resp.data
                })
            },
            isParticipant(tournament) {
                return this.$store.getters.getProfile.joinedTournaments.filter(trnmt => {
                    return trnmt.id === tournament.id;
                }).length !== 0;
            },
            openTournament(id) {
                this.$router.push("/tournament/" + id);
            }
        },

        mounted() {
            this.loadData()
        }
    }
</script>

<style scoped>
</style>