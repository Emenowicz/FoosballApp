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

    export default {
        name: "NewTournament",
        data() {
            return {
                name: ''
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

            }
        }
    }
</script>

<style scoped>

</style>