<template>
    <v-fade-transition>
        <v-container fluid fill-height grid-list-xl>
            <v-layout row align-center justify-center>
                <v-flex d-flex xs12 sm8>
                    <v-card>
                        <v-card-title primary class="title">
                            Utwórz nowy turniej
                        </v-card-title>
                        <v-card-text fill-height>
                            <v-alert class="my-0" :value="hasErrors" type="error" @click="closeAlert"
                                    transition="fade-transition">
                                {{errors[0]}}
                            </v-alert>
                            <v-form @submit.prevent="submitForm">
                                <v-text-field prepend-icon="home" name="Name" label="Nazwa turnieju" counter="40"
                                        v-model="name" :error-messages="nameErrors" @blur="$v.name.$touch()"
                                        @input="$v.name.$touch()"></v-text-field>

                                <v-textarea prepend-icon="subject" name="Description" label="Opis turnieju" counter
                                        v-model="description"></v-textarea>
                                <v-select prepend-icon="people" name="teamsNeeded" label="Liczba drużyn w turnieju"
                                        v-model="teamsNeeded" :items="teamsNeeds"></v-select>
                                <v-select prepend-icon="gamepad" name="RuleSet" label="Zestaw zasad" v-model="ruleSetSelected"
                                        :items="ruleSets" item-text="value.name" item-value="value"></v-select>
                                <div v-if="isCustomSelected">
                                    <p>Dodaj własne zasady</p>
                                    <v-layout row wrap align-center justify-center>
                                        <v-text-field prepend-icon="people" name="playersInTeam"
                                                label="Liczba graczy w drużynie"
                                                class="mx-4" v-model="ruleSetSelected.teamsNeeded"
                                                :mask="mask"></v-text-field>
                                        <v-text-field prepend-icon="star_border" name="pointsToWin"
                                                label="Punkty do zwycięstwa setu"
                                                class="mx-4" v-model="ruleSetSelected.pointsToWin"
                                                :mask="pointsMask"></v-text-field>
                                        <v-text-field prepend-icon="star" name="roundsToWin" label="Sety do zwycięstwa meczu"
                                                class="mx-4" v-model="ruleSetSelected.roundsToWin"
                                                :mask="mask"></v-text-field>

                                    </v-layout>
                                </div>
                                <v-layout justify-center>
                                    <v-btn type="submit">Utwórz turniej</v-btn>
                                </v-layout>
                            </v-form>
                        </v-card-text>
                    </v-card>
                </v-flex>
            </v-layout>
        </v-container>
    </v-fade-transition>
</template>

<script>

    import required from "vuelidate/src/validators/required";
    import minLength from "vuelidate/src/validators/minLength";
    import maxLength from "vuelidate/src/validators/maxLength";
    import axios from 'axios';
    import ApiConstants from "../constants/ApiConstants";
    import {USER_REQUEST} from "../store/actions/user";

    export default {
        name: "NewTournament",
        data() {
            return {
                name: '',
                description: '',
                teamsNeeded: '',
                ruleSetSelected: {},
                ruleSets: [],
                teamsNeeds: ['4','8','16','32'],
                mask: "#",
                pointsMask: "##",
                errors: []
            }
        },
        validations: {
            name: {
                required,
                minLength: minLength(4),
                maxLength: maxLength(40)
            }
        },
        computed: {
            hasErrors() {
                return !!this.errors.length
            },
            nameErrors() {
                const errors = []
                if (!this.$v.name.$dirty) return errors
                !this.$v.name.required && errors.push('Nazwa jest wymagana')
                !this.$v.name.minLength && errors.push('Nazwa musi składać się z conajmniej 4 znaków')
                !this.$v.name.maxLength && errors.push('Nazwa musi składać się z maksymalnie 40 znaków')
                return errors
            },
            isCustomSelected() {
                return this.ruleSetSelected.name === "Niestandardowy"
            }
        },
        methods: {
            submitForm() {
                axios({
                    url: ApiConstants.CREATE_NEW_TOURNAMENT,
                    data: {
                        name: this.name,
                        description: this.description,
                        teamsNeeded: this.teamsNeeded,
                        ruleSet: this.ruleSetSelected,
                    },
                    method: "POST"
                }).then(() => {
                    this.$store.dispatch(USER_REQUEST)
                    this.$router.push("/")
                }).catch(err => {
                    this.errors = [...this.errors, err.response.data]
                })
            },
            loadRuleSets() {
                axios({
                    url: ApiConstants.GET_DEFAULT_RULESETS,
                    method: "GET"
                }).then(resp => {
                    const ruleSetsFromResp = resp.data.map(function (ruleSet) {
                        return {name: ruleSet.name, value: ruleSet}
                    })
                    this.ruleSets.push.apply(this.ruleSets, ruleSetsFromResp)
                    this.ruleSets.push({value: {name: "Niestandardowy", pointsToWin: '', roundsToWin: '', teamSize: ''}})
                }).catch(() => {
                    this.ruleSets.push({value: {name: "Niestandardowy", pointsToWin: '', roundsToWin: '', teamSize: ''}})
                })
            },
            closeAlert() {
                this.errors = []
            }
        },
        mounted() {
            this.loadRuleSets()
        }
    }
</script>

<style scoped>

</style>