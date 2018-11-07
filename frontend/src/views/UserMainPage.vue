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
                                                <p>Opis: {{props.item.description}}</p>
                                                <v-btn @click="openTournamentPage(props.item.id)">Zobacz turniej</v-btn>
                                                <v-btn>Usuń turniej</v-btn>
                                                <v-btn v-if="props.item.status==='Otwarty'">Rozpocznij</v-btn>
                                                <v-btn v-if="props.item.status==='W trakcie'">Zakończ</v-btn>
                                            </v-card-text>
                                        </v-card>
                                    </v-flex>
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
                                    <td>{{props.item.ruleSet.pointsToWin}}</td>
                                    <td>{{props.item.ruleSet.roundsToWin}}</td>
                                    <td>{{props.item.ruleSet.teamSize}}</td>
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

    export default {
        name: "UserMainPage",
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
            }
        },
        computed: {
            ownedTournaments: function () {
                return this.$store.getters.getProfile.ownedTournaments
            },
            joinedTournaments: function () {
                return this.$store.getters.getProfile.joinedTournaments
            }
        },
        methods: {
            openTournamentPage(tournamentId) {
                this.$router.push({path: "/tournament/"+tournamentId})
            }
        }
    }
</script>

<style scoped>

</style>