<template>
    <v-fade-transition>
        <v-container fluid fill-height grid-list-xl>
            <v-layout row align-center justify-center>
                <v-flex d-flex fill-height>
                    <v-card>
                        <v-alert class="my-4" :value="hasErrors" type="error" @click="closeAlerts" transition="fade-transition">
                            {{errors[0]}}
                        </v-alert>
                        <v-card-title class="headline pb-0">
                            <p class="text-uppercase pb-0 mb-0">{{tournament.name}}</p>
                            <v-spacer />
                            <p class="subheading pb-0 mb-0">Status: &nbsp;</p>
                            <p class="subheading pb-0 mb-0 green--text"
                                    v-if="tournament.status==='Otwarty'">{{tournament.status}}</p>
                            <p class="subheading pb-0 mb-0 orange--text"
                                    v-if="tournament.status==='Trwający'">{{tournament.status}}</p>
                            <p class="subheading pb-0 mb-0 red--text"
                                    v-if="tournament.status==='Zakończony'">{{tournament.status}}</p>
                        </v-card-title>
                        <v-card-text class="pt-0">
                            <v-layout wrap align-center justify-space-around>
                                <v-flex xs12 sm8 class="subheading" style="overflow-wrap: break-word">{{tournament.description}}
                                </v-flex>
                                <v-flex>
                                    <v-layout column align-center justify-center>
                                        <v-flex v-if="!!tournament.owner" xs12 sm4
                                                class="text-xs-center">Właściciel turnieju: {{tournament.owner.username}}
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
                            <v-layout v-if="!!tournament.ruleSet" wrap align-center justify-space-around>
                                <v-flex>
                                    <v-text-field flat readonly label="Wymagana liczba drużyn" v-model="tournament.teamsNeeded">
                                    </v-text-field>
                                </v-flex>
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
                                <v-flex v-if="tournament.status==='Zakończony'" xs12>
                                    <v-card>
                                        <v-card-title class="text-xs-center">
                                            <p class="headline">Zwycięzca: {{tournament.winner.name}}</p>
                                        </v-card-title>
                                        <v-card-text>
                                            <v-list>
                                                <v-list-tile v-for="(player,index) in tournament.winner.players" :key="index">
                                                    <v-list-tile-content>{{index}}. {{player.username}}</v-list-tile-content>
                                                </v-list-tile>
                                            </v-list>
                                        </v-card-text>
                                    </v-card>
                                </v-flex>
                                <v-flex>
                                    <v-card>
                                        <v-card-title>
                                            <p class="subheading">Drużyny</p>
                                            <v-spacer></v-spacer>
                                            <v-btn v-if="!isParticipant && tournament.status === 'Otwarty' && !tournament.readyToStart"
                                                    class="green"
                                                    @click="dialog=!dialog"
                                                    dark>Dołącz do turnieju
                                            </v-btn>
                                        </v-card-title>
                                        <v-card-text>
                                            <v-data-iterator :items="tournament.teams" content-tag="v-layout" row wrap
                                                    hide-actions>
                                                <v-flex slot="item" slot-scope="props" xs12 md6>
                                                    <v-card v-if="props.item.size!==0">
                                                        <v-card-title class="pb-1">
                                                            <h4>{{props.item.name}}</h4>
                                                            <v-spacer></v-spacer>
                                                            <v-btn v-if="!isParticipant && tournament.status==='Otwarty' && !tournament.readyToStart && props.item.players.length < tournament.ruleSet.teamSize"
                                                                    @click="joinToTeam(props.item)" icon
                                                                    dark
                                                            >
                                                                <v-icon color="green">add</v-icon>
                                                            </v-btn>
                                                            <v-btn v-if="isInTeam(props.item.players)"
                                                                    @click="leaveTeam(props.item)" icon
                                                                    dark>
                                                                <v-icon color="red">clear</v-icon>
                                                            </v-btn>
                                                        </v-card-title>
                                                        <v-card-text class="pt-1">
                                                            <v-list>
                                                                <v-subheader class="pa-1">Skład drużyny:
                                                                    <v-spacer />
                                                                    <h4>{{props.item.players.length}}/{{tournament.ruleSet.teamSize}}</h4>
                                                                </v-subheader>

                                                                <v-list-tile class="py-1" v-for="player in props.item.players"
                                                                        :key="player.username">
                                                                    <v-list-tile-content>
                                                                        <v-list-tile-title
                                                                                v-text="player.username"></v-list-tile-title>
                                                                    </v-list-tile-content>
                                                                </v-list-tile>
                                                            </v-list>
                                                            <v-layout justify-end>
                                                                <v-dialog v-model="passwordDialog" persistent max-width="300">
                                                                    <v-card>
                                                                        <v-card-title
                                                                                class="headline">Podaj hasło dostępu do drużyny
                                                                        </v-card-title>
                                                                        <v-card-text>
                                                                            <v-form @submit.prevent="joinToTeam(props.item)">
                                                                                <v-text-field prepend-icon="lock"
                                                                                        v-model="password"
                                                                                        name="Password"
                                                                                        label="Hasło dołączania" type="password"
                                                                                        :error-messages="passwordErrors"
                                                                                        @blur="$v.password.$touch()"
                                                                                        @click="$v.password.$touch()"></v-text-field>
                                                                                <v-text-field prepend-icon="lock"
                                                                                        v-model="confirmPassword"
                                                                                        name="ConfirmPassword"
                                                                                        label="Potwierdź hasło" type="password"
                                                                                        :error-messages="confirmPasswordErrors"
                                                                                        @blur="$v.confirmPassword.$touch()"
                                                                                        @click="$v.confirmPassword.$touch()"></v-text-field>
                                                                                <v-card-actions>
                                                                                    <v-btn flat type="submit" color="green"
                                                                                            @click.native="joinToTeam(props.item)">Potwierdź
                                                                                    </v-btn>
                                                                                    <v-btn flat color="green"
                                                                                            @click="passwordDialog=false">Anuluj
                                                                                    </v-btn>
                                                                                </v-card-actions>
                                                                            </v-form>
                                                                        </v-card-text>
                                                                    </v-card>
                                                                </v-dialog>
                                                            </v-layout>
                                                        </v-card-text>
                                                    </v-card>
                                                </v-flex>
                                            </v-data-iterator>
                                        </v-card-text>
                                    </v-card>
                                </v-flex>
                                <v-flex v-if="tournament.status!=='Otwarty'">
                                    <v-card>
                                        <v-card-title>
                                            <p class="subheading">Mecze</p>
                                        </v-card-title>
                                        <v-card-text>
                                            <v-card v-for="match in tournament.matches" :key="match.id" class="text-xs-center">
                                                <v-card-text>
                                                    <v-layout justify-space-between>
                                                        <v-flex xs5>
                                                            <h3 class="subheading">{{match.teamOne.name}}</h3>
                                                        </v-flex>
                                                        <v-flex xs2>
                                                            <h3 class="subheading">vs.</h3>
                                                        </v-flex>
                                                        <v-flex xs5>
                                                            <h3 v-if="!!match.teamTwo"
                                                                    class="subheading">{{match.teamTwo.name}}</h3>
                                                            <h3 v-else class="subheading">Oczekiwanie na drużynę</h3>
                                                        </v-flex>
                                                    </v-layout>
                                                    <h2 class="display-1">{{match.scoreOne}} - {{match.scoreTwo}}</h2>
                                                    <v-btn v-if="isInMatch(match.id)"></v-btn>
                                                </v-card-text>
                                            </v-card>
                                        </v-card-text>
                                    </v-card>
                                </v-flex>
                            </v-layout>
                            <v-layout v-if="isOwner">
                                <v-speed-dial fixed right bottom class="floating-button-corner">
                                    <v-btn slot="activator" color="blue darken-2" dark fab>
                                        <v-icon>account_circle</v-icon>
                                        <v-icon>close</v-icon>
                                    </v-btn>
                                    <v-btn v-if="tournament.status==='Otwarty' && tournament.readyToStart" @click="startTournament" fab
                                            dark small
                                            color="green">
                                        <v-icon>play_arrow</v-icon>
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
            <v-dialog v-model="dialog" fullscreen hide-overlay transition="dialog-bottom-transition">
                <v-card>
                    <v-toolbar dark color="green">
                        <v-btn icon dark @click.native="dialog = false">
                            <v-icon>close</v-icon>
                        </v-btn>
                        <v-toolbar-title>Utwórz drużynę</v-toolbar-title>
                        <v-spacer></v-spacer>
                        <v-toolbar-items>
                            <v-btn dark flat @click.native="createNewTeam">Dołącz</v-btn>
                        </v-toolbar-items>
                    </v-toolbar>
                    <v-card-text>
                        <v-layout wrap justify-space-around>
                            <v-flex xs12 md4>
                                <v-card>
                                    <v-card-title class="headline">
                                        Nowa drużyna
                                    </v-card-title>
                                    <v-card-text>
                                        <v-form @submit.prevent="createNewTeam">
                                            <v-text-field v-model="newTeam.name" label="Nazwa drużyny"></v-text-field>
                                            <v-radio-group v-model="newTeam.privacy" label="Prywatność">
                                                <v-layout>
                                                    <v-radio value="open" label="Otwarta" color="green"></v-radio>
                                                    <v-radio value="private" label="Na zaproszenie" color="red"></v-radio>
                                                </v-layout>
                                            </v-radio-group>
                                            <v-layout v-if="newTeam.privacy==='private'">
                                                <v-text-field prepend-icon="lock" v-model="password" name="Password"
                                                        label="Hasło dołączania" type="password"
                                                        :error-messages="passwordErrors" @blur="$v.password.$touch()"
                                                        @click="$v.password.$touch()"></v-text-field>
                                                <v-text-field prepend-icon="lock" v-model="confirmPassword" name="ConfirmPassword"
                                                        label="Potwierdź hasło" type="password"
                                                        :error-messages="confirmPasswordErrors"
                                                        @blur="$v.confirmPassword.$touch()"
                                                        @click="$v.confirmPassword.$touch()"></v-text-field>
                                            </v-layout>
                                        </v-form>
                                    </v-card-text>
                                </v-card>
                            </v-flex>
                        </v-layout>
                    </v-card-text>
                </v-card>
            </v-dialog>
        </v-container>
    </v-fade-transition>
</template>

<script>
    import axios from 'axios'
    import ApiConstants from "../constants/ApiConstants";
    import required from "vuelidate/src/validators/required";
    import minLength from "vuelidate/src/validators/minLength";
    import sameAs from "vuelidate/src/validators/sameAs";
    import {USER_REQUEST} from "../store/actions/user";

    export default {
        name: "TournamentPage",
        data() {
            return {
                tournament: {},
                newTeam: {
                    name: '',
                    privacy: 'open',
                },
                password: '',
                confirmPassword: '',
                dialog: false,
                passwordDialog: false,
                errors: []
            }
        },
        mounted() {
            this.loadData()
        },
        methods: {
            loadData() {
                axios({
                    url: ApiConstants.GET_TOURNAMENT + this.$route.params.id,
                    method: "GET"
                }).then(resp => {
                    this.tournament = resp.data
                    this.$store.dispatch(USER_REQUEST)
                }).catch(err => {
                    this.closeAlerts()
                    this.errors = [...this.errors, err.response.data]
                })
            },
            createNewTeam() {
                if (!this.isParticipant)
                    axios({
                        url: ApiConstants.CREATE_TEAM(this.$route.params.id),
                        data: {
                            name: this.newTeam.name,
                            privacy: this.newTeam.privacy,
                            password: this.newTeam.privacy === "private" ? this.password : ''

                        },
                        method: "POST"
                    }).then(() => {
                        this.dialog = false
                        this.loadData()
                    }).catch(err => {
                        this.closeAlerts()
                        this.errors = [...this.errors, err.response.data]
                    })
            },
            joinToTeam(team) {
                if (team.private && !this.passwordDialog) {
                    this.passwordDialog = true
                } else if (!this.isParticipant) {
                    axios({
                        url: ApiConstants.JOIN_TO_TEAM(team.id),
                        data: {
                            password: team.private ? this.password : ''
                        },
                        method: "POST"
                    }).then(() => {
                        this.loadData()
                        this.closeAlerts()
                    }).catch(err => {
                        this.closeAlerts()
                        this.errors = [...this.errors, err.response.data]
                    }).finally(() => {
                        this.passwordDialog = false
                        setTimeout(() => {
                            this.password = ''
                            this.confirmPassword = ''
                        }, 1000)
                    })
                }
                setTimeout(() => {

                }, 1000)
            },
            leaveTeam(team) {
                axios({
                    url: ApiConstants.LEAVE_TEAM(team.id),
                    method: "POST"
                }).then(() => {
                    this.loadData()
                }).catch(err => {
                    this.closeAlerts()
                    this.errors = [...this.errors, err.response.data]

                })
            },
            startTournament() {
                if (this.isTournamentFull) {
                    axios({
                        url: ApiConstants.START_TOURNAMENT(this.tournament.id),
                        method: "POST"
                    }).then(() => {
                        this.loadData()
                    }).catch(err => {
                        this.errors = [...this.errors, err.response.data]
                    })
                } else {
                    this.errors = [...this.errors, "Wystąpił błąd"]
                }

            }
            ,
            isInMatch(id) {
                return this.$store.getters.getProfile.teams.filter(team => {
                    return team.id === id;
                }).length !== 0;
            }
            ,
            closeAlerts() {
                this.errors = []
            }
            ,
            isInTeam(players) {
                let isInTeam = false
                players.forEach(player => {
                    if (player.id === this.$store.getters.getProfile.id) {
                        isInTeam = true;
                    }
                })
                return isInTeam
            }
        },
        validations: {
            password: {
                required,
                minLength: minLength(4)
            },
            confirmPassword: {
                required,
                sameAsPassword: sameAs('password')
            },
        },
        computed: {
            isParticipant() {
                return this.$store.getters.getProfile.joinedTournaments.filter(tournament => {
                    return tournament.id === this.tournament.id;
                }).length !== 0;
            },
            isOwner() {
                return this.$store.getters.getProfile.ownedTournaments.filter(tournament => {
                    return tournament.id === this.tournament.id;
                }).length !== 0;
            },
            isTournamentFull() {
                return this.tournament.teams.length === this.tournament.teamsNeeded
            },
            hasErrors() {
                return !!this.errors.length
            },
            passwordErrors() {
                const errors = []
                if (!this.$v.password.$dirty) return errors
                !this.$v.password.required && errors.push('Hasło jest wymagane')
                !this.$v.password.minLength && errors.push('Hasło musi mieć minimum 4 znaki')

                return errors
            },
            confirmPasswordErrors() {
                const errors = []
                if (!this.$v.confirmPassword.$dirty) return errors
                !this.$v.confirmPassword.required && errors.push('Powtórzenie hasła jest wymagane')
                !this.$v.confirmPassword.sameAsPassword && errors.push('Hasła muszą się zgadzać')
                return errors
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