<template>
    <v-fade-transition>
        <v-container fluid fill-height grid-list-xl>
            <v-layout row align-center justify-center>
                <v-flex d-flex xs12 sm6>
                    <v-card>
                        <v-card-title primary class="title">
                            Utwórz nowy turniej
                        </v-card-title>
                        <v-card-text fill-height>
                            <v-text-field prepend-icon="home" name="Name" label="Nazwa turnieju" counter="40"
                                    v-model="name" :error-messages="nameErrors" @blur="$v.name.$touch()"
                                    @input="$v.name.$touch()"></v-text-field>

                            <v-textarea prepend-icon="subject" name="Description" label="Opis turnieju" counter
                                    v-model="description"></v-textarea>
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
    import ApiConstants from "../libs/ApiConstants";

    export default {
        name: "NewTournament",
        data() {
            return {
                name: '',
                description: '',
                ruleSetSelect: {},
                ruleSets: []
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
            nameErrors() {
                const errors = []
                if (!this.$v.name.$dirty) return errors
                !this.$v.name.required && errors.push('Nazwa jest wymagana')
                !this.$v.name.minLength && errors.push('Nazwa musi składać się z conajmniej 4 znaków')
                !this.$v.name.maxLength && errors.push('Nazwa musi składać się z maksymalnie 40 znaków')
                return errors
            }
        },
        methods: {
            submitForm() {

            },
            loadRuleSets() {
                axios({
                    url: ApiConstants.GET_DEFAULT_RULESETS,
                    method: "GET"
                }).then(resp => {
                    this.ruleSets.push.apply(this.ruleSets,resp.data)
                    this.ruleSets.push({name: "Niestandardowy"})
                }).catch(() => {
                    this.ruleSets.push({name: "Niestandardowy"})
                })
            }
        },
        mounted() {
            this.loadRuleSets()
        }
    }
</script>

<style scoped>

</style>