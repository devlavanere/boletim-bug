import spock.lang.Specification
import spock.lang.Unroll

class BoletimSpecification extends Specification {

	def "calcula a média somando todas as notas corretamente"() {
		given: "uma lista de notas"
			List notas = [8.0, 7.5, 9.0]

		when: "calculamos a média"
			def resultado = calcularMedia(notas)

		then: "a média é a soma de todas as notas dividida pela quantidade"
			Math.abs(resultado - 8.166666666666666) < 0.001
	}

	def "calcula a média com notas diferentes"() {
		given: "as notas do Diego"
			List notas = [3.0, 9.0, 9.0]

		when: "calculamos a média"
			def resultado = calcularMedia(notas)

		then: "a média correta é 7.0"
			Math.abs(resultado - 7.0) < 0.001
	}

	def "calcula a média com notas iguais"() {
		given: "notas iguais"
			List notas = [6.0, 6.0, 6.0]

		when: "calculamos a média"
			def resultado = calcularMedia(notas)

		then: "a média é igual à nota repetida"
			Math.abs(resultado - 6.0) < 0.001
	}

	def "calcula a média com notas zeradas"() {
		given: "notas zeradas"
			List notas = [0.0, 0.0, 0.0]

		when: "calculamos a média"
			def resultado = calcularMedia(notas)

		then: "a média é zero"
			Math.abs(resultado - 0.0) < 0.001
	}

	def "calcula a média com nota máxima"() {
		given: "notas máximas"
			List notas = [10.0, 10.0, 10.0]

		when: "calculamos a média"
			def resultado = calcularMedia(notas)

		then: "a média é 10.0"
			Math.abs(resultado - 10.0) < 0.001
	}

	def "aluno com média exatamente 7.0 é aprovado"() {
		given: "média exatamente no limite de aprovação"
			def media = 7.0
			def mediaAprovacao = 7.0
			def mediaRecuperacao = 5.0

		when: "verificamos a situação"
			def resultado = situacao(media, mediaAprovacao, mediaRecuperacao)

		then: "o aluno é aprovado"
			resultado == "Aprovado"
	}

	def "aluno com média acima de 7.0 é aprovado"() {
		given: "média acima do limite de aprovação"
			def media = 8.17
			def mediaAprovacao = 7.0
			def mediaRecuperacao = 5.0

		when: "verificamos a situação"
			def resultado = situacao(media, mediaAprovacao, mediaRecuperacao)

		then: "o aluno é aprovado"
			resultado == "Aprovado"
	}

	def "aluno com média exatamente 5.0 vai para recuperação"() {
		given: "média exatamente no limite de recuperação"
			def media = 5.0
			def mediaAprovacao = 7.0
			def mediaRecuperacao = 5.0

		when: "verificamos a situação"
			def resultado = situacao(media, mediaAprovacao, mediaRecuperacao)

		then: "o aluno vai para recuperação"
			resultado == "Recuperação"
	}

	def "aluno com média entre 5.0 e 7.0 vai para recuperação"() {
		given: "média entre recuperação e aprovação"
			def media = 6.0
			def mediaAprovacao = 7.0
			def mediaRecuperacao = 5.0

		when: "verificamos a situação"
			def resultado = situacao(media, mediaAprovacao, mediaRecuperacao)

		then: "o aluno vai para recuperação"
			resultado == "Recuperação"
	}

	def "aluno com média abaixo de 5.0 é reprovado"() {
		given: "média abaixo do limite de recuperação"
			def media = 4.9
			def mediaAprovacao = 7.0
			def mediaRecuperacao = 5.0

		when: "verificamos a situação"
			def resultado = situacao(media, mediaAprovacao, mediaRecuperacao)

		then: "o aluno é reprovado"
			resultado == "Reprovado"
	}

	def "aluno com média zero é reprovado"() {
		given: "média zero"
			def media = 0.0
			def mediaAprovacao = 7.0
			def mediaRecuperacao = 5.0

		when: "verificamos a situação"
			def resultado = situacao(media, mediaAprovacao, mediaRecuperacao)

		then: "o aluno é reprovado"
			resultado == "Reprovado"
	}

	def "aluno com média 6.99 não é aprovado"() {
		given: "média logo abaixo do limite de aprovação"
			def media = 6.99
			def mediaAprovacao = 7.0
			def mediaRecuperacao = 5.0

		when: "verificamos a situação"
			def resultado = situacao(media, mediaAprovacao, mediaRecuperacao)

		then: "o aluno vai para recuperação, não é aprovado"
			resultado == "Recuperação"
	}

	def "aluno com média 4.99 é reprovado"() {
		given: "média logo abaixo do limite de recuperação"
			def media = 4.99
			def mediaAprovacao = 7.0
			def mediaRecuperacao = 5.0

		when: "verificamos a situação"
			def resultado = situacao(media, mediaAprovacao, mediaRecuperacao)

		then: "o aluno é reprovado"
			resultado == "Reprovado"
	}

	def "todos os alunos da turma produzem o resultado esperado"() {
		given: "os dados da turma"
			def alunos = [
				"Ana":   [8.0, 7.5, 9.0],
				"Bruno": [5.0, 6.0, 7.0],
				"Carla": [10.0, 4.0, 7.0],
				"Diego": [3.0, 9.0, 9.0],
			]
			def mediaAprovacao = 7.0
			def mediaRecuperacao = 5.0

		when: "calculamos a média e situação de cada aluno"
			def resultados = [:]
			alunos.each { nome, notas ->
				def media = calcularMedia(notas)
				def situacaoAluno = situacao(media, mediaAprovacao, mediaRecuperacao)
				resultados[nome] = [media: media, situacao: situacaoAluno]
			}

		then: "os resultados batem com o esperado no README"
			Math.abs(resultados["Ana"].media - 8.166666666666666) < 0.001
			resultados["Ana"].situacao == "Aprovado"

			Math.abs(resultados["Bruno"].media - 6.0) < 0.001
			resultados["Bruno"].situacao == "Recuperação"

			Math.abs(resultados["Carla"].media - 7.0) < 0.001
			resultados["Carla"].situacao == "Aprovado"

			Math.abs(resultados["Diego"].media - 7.0) < 0.001
			resultados["Diego"].situacao == "Aprovado"
	}

	// ---------------------------------------------------------------
	// Funções auxiliares copiadas de Boletim.groovy para teste
	// ---------------------------------------------------------------

	def calcularMedia(List notas) {
		def soma = 0
		for (int i = 0; i < notas.size(); i++) {
			soma += notas[i]
		}
		return soma / notas.size()
	}

	def situacao(media, mediaAprovacao, mediaRecuperacao) {
		if (media >= mediaAprovacao) {
			return "Aprovado"
		} else if (media >= mediaRecuperacao) {
			return "Recuperação"
		} else {
			return "Reprovado"
		}
	}
}
