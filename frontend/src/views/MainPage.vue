<template>
    <v-container fluid fill-height grid-list-xl>
        <v-layout row wrap justify-center>
            <v-flex d-flex xs12 sm6>
                <v-card>
                    <v-card-title primary class="title">Aktualne turnieje</v-card-title>
                    <v-card-text>
                        <v-data-table :headers="tournamentHeaders" :items="allTournaments">
                            <template slot="items" slot-scope="props">
                                <td>{{props.item.name}}</td>
                                <td>{{props.item.status}}</td>
                            </template>
                        </v-data-table>
                    </v-card-text>

                </v-card>
            </v-flex>
            <v-flex d-flex xs12 sm6>
                <v-card>
                    <v-card-title primary class="title">Moje turnieje</v-card-title>
                    <v-card-text>
                        <v-data-table :headers="tournamentHeaders" :items="allTournaments">
                            <template slot="items" slot-scope="props">
                                <td>{{props.item.name}}</td>
                                <td>{{props.item.status}}</td>
                            </template>
                        </v-data-table>
                    </v-card-text>
                </v-card>
            </v-flex>
        </v-layout>
    </v-container>
</template>

<script>
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
                    }
                ],
                allTournaments: [
                    {
                        name: 'dawid',
                        status: 'created'
                    }
                ]
            }
        }
    }
</script>

<style scoped>

</style>