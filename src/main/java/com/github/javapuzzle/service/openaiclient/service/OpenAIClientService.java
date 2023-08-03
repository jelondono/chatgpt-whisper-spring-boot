package com.github.javapuzzle.service.openaiclient.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javapuzzle.service.openaiclient.openaiclient.OpenAIClient;
import com.github.javapuzzle.service.openaiclient.openaiclient.OpenAIClientConfig;
import com.github.javapuzzle.service.openaiclient.model.request.ChatGPTRequest;
import com.github.javapuzzle.service.openaiclient.model.request.WhisperTranscriptionRequest;
import com.github.javapuzzle.service.openaiclient.model.request.TranscriptionRequest;
import com.github.javapuzzle.service.openaiclient.model.response.ChatGPTResponse;
import com.github.javapuzzle.service.openaiclient.model.request.ChatRequest;
import com.github.javapuzzle.service.openaiclient.model.request.Message;
import com.github.javapuzzle.service.openaiclient.model.response.WhisperTranscriptionResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OpenAIClientService {

    private final OpenAIClient openAIClient;
    private final OpenAIClientConfig openAIClientConfig;

    private final static String ROLE_USER = "user";

    public ChatGPTResponse chat(ChatRequest chatRequest){
        Message message = Message.builder()
                .role(ROLE_USER)
                .content(chatRequest.getQuestion())
                .build();
        ChatGPTRequest chatGPTRequest = ChatGPTRequest.builder()
                .model(openAIClientConfig.getModel())
                .messages(Collections.singletonList(message))
                .build();
        return openAIClient.chat(chatGPTRequest);
    }

    public WhisperTranscriptionResponse createTranscription(TranscriptionRequest transcriptionRequest){
        WhisperTranscriptionRequest whisperTranscriptionRequest = WhisperTranscriptionRequest.builder()
                .model(openAIClientConfig.getAudioModel())
                .file(transcriptionRequest.getFile())
                .build();
        return openAIClient.createTranscription(whisperTranscriptionRequest);
    }

    public JsonNode learnListingsAndAnalyzeTranscription( String medicalTranscription) {
        // Primero, un mensaje para "enseñar" a GPT-3 los listados
        System.out.println("********* INGRESA A LA PETICION*****************");
       // String listings ="{\n    \"list\": {\n        \"medicaments\": [\n            {\n                \"id\": 1,\n                \"nomMedicamento\": \"Acetaminofen\"\n            },\n            {\n                \"id\": 2,\n                \"nomMedicamento\": \"Dipirona\"\n            },\n            {\n                \"id\": 3,\n                \"nomMedicamento\": \"Diclofenaco\"\n            }\n        ],\n        \"diagnostics\": [\n            {\n                \"id\": 1,\n                \"name\": \"Gripa\"\n            },\n            {\n                \"id\": 2,\n                \"name\": \"Cancer\"\n            },\n            {\n                \"id\": 3,\n                \"name\": \"Diabetes\"\n            }\n        ],\n        \"relationShip\": [\n            {\n                \"id\": 1,\n                \"name\": \"Hijo\",\n                \"description\": \"Hijo\"\n            },\n            {\n                \"id\": 2,\n                \"name\": \"Hermanos\",\n                \"description\": \"Hermanos\"\n            },\n            {\n                \"id\": 3,\n                \"name\": \"Madre\",\n                \"description\": \"Madre\"\n            },\n            {\n                \"id\": 4,\n                \"name\": \"Padre\",\n                \"description\": \"Padre\"\n            },\n            {\n                \"id\": 5,\n                \"name\": \"Tío\",\n                \"description\": \"Tío\"\n            },\n            {\n                \"id\": 6,\n                \"name\": \"Primo\",\n                \"description\": \"Primo\"\n            },\n            {\n                \"id\": 7,\n                \"name\": \"Familiar en segundo grado\",\n                \"description\": \"Familiar en segundo grado\"\n            },\n            {\n                \"id\": 8,\n                \"name\": \"Colega\",\n                \"description\": \"Colega\"\n            },\n            {\n                \"id\": 9,\n                \"name\": \"Nieto\",\n                \"description\": \"Nieto\"\n            },\n            {\n                \"id\": 10,\n                \"name\": \"Abuelo\",\n                \"description\": \"Abuelo\"\n            },\n            {\n                \"id\": 11,\n                \"name\": \"Amigo\",\n                \"description\": \"Amigo\"\n            },\n            {\n                \"id\": 12,\n                \"name\": \"Ninguno\",\n                \"description\": \"Ninguno\"\n            },\n            {\n                \"id\": 13,\n                \"name\": \"Otro\",\n                \"description\": \"Otro\"\n            },\n            {\n                \"id\": 14,\n                \"name\": \"No aplica\",\n                \"description\": \"No aplica\"\n            },\n            {\n                \"id\": 15,\n                \"name\": \"Cónyuge\",\n                \"description\": \"Cónyuge\"\n            },\n            {\n                \"id\": 16,\n                \"name\": \"Padres\",\n                \"description\": \"Padres\"\n            }\n        ]\n    },\n    \"vitalSignsObject\": {\n        \"weight\": \"\",\n        \"height\": \"\",\n        \"pulse\": \"\"\n    },\n    \"encounterCommonInfo\": {\n        \"patientcompanionrelationship\": \"Parentesco de el paciente, el valor es de tipo String y corresponde a uno de los relationShip compartidos asocia y obten el name: [relationShip]\",\n        \"medicamentsList\": \"Medicamentos, El valor es de tipo arreglo de objetos y corresponde a una o varios de los elementos de medicamentos compartidos: [medicaments]\",\n        \"diagnosticList\": \"Diagnosticos, El valor es de tipo arreglo de objetos y corresponde a una o varios de los elementos de diagnostics compartidos: [diagnostic]\",\n        \"namePatient\": \" El valor es de tipo string y corresponde al nombre del paciente\",\n        \"agePatient\": \" El valor es de tipo String y corresponde a la edad\",\n        \"dateBirthDate\": \" El valor es de tipo Date y en formato yyyy-MM-dd y corresponde a la fecha de nacimiento\",\n        \"vitalSigns\": \"El valor es un objeto de tipo vitalSignsObject y corresponde a los signos vitales identificados\"\n    }\n}";
        String listings = "{\n" +
                "    \"list\": {\n" +
                "        \"medicaments\": [\n" +
                "            {\n" +
                "                \"id\": 1,\n" +
                "                \"nomMedicamento\": \"Acetaminofen\"\n" +
                "            },\n" +
                "            {\n" +
                "                \"id\": 2,\n" +
                "                \"nomMedicamento\": \"Omeprazol\"\n" +
                "            },\n" +
                "            {\n" +
                "                \"id\": 3,\n" +
                "                \"nomMedicamento\": \"Diclofenaco\"\n" +
                "            }\n" +
                "        ],\n" +
                "        \"diagnostics\": [\n" +
                "            {\n" +
                "                \"id\": 1,\n" +
                "                \"name\": \"Gripa\"\n" +
                "            },\n" +
                "            {\n" +
                "                \"id\": 2,\n" +
                "                \"name\": \"Gastritis\"\n" +
                "            },\n" +
                "            {\n" +
                "                \"id\": 3,\n" +
                "                \"name\": \"Diabetes\"\n" +
                "            }\n" +
                "        ],\n" +
                "        \"relationShip\": [\n" +
                "            {\n" +
                "                \"id\": 1,\n" +
                "                \"name\": \"Hijo\",\n" +
                "                \"description\": \"Hijo\"\n" +
                "            },\n" +
                "            {\n" +
                "                \"id\": 2,\n" +
                "                \"name\": \"Hermanos\",\n" +
                "                \"description\": \"Hermanos\"\n" +
                "            },\n" +
                "            {\n" +
                "                \"id\": 3,\n" +
                "                \"name\": \"Madre\",\n" +
                "                \"description\": \"Madre\"\n" +
                "            },\n" +
                "            {\n" +
                "                \"id\": 4,\n" +
                "                \"name\": \"Padre\",\n" +
                "                \"description\": \"Padre\"\n" +
                "            },\n" +
                "            {\n" +
                "                \"id\": 5,\n" +
                "                \"name\": \"Tío\",\n" +
                "                \"description\": \"Tío\"\n" +
                "            },\n" +
                "            {\n" +
                "                \"id\": 6,\n" +
                "                \"name\": \"Primo\",\n" +
                "                \"description\": \"Primo\"\n" +
                "            },\n" +
                "            {\n" +
                "                \"id\": 7,\n" +
                "                \"name\": \"Familiar en segundo grado\",\n" +
                "                \"description\": \"Familiar en segundo grado\"\n" +
                "            },\n" +
                "            {\n" +
                "                \"id\": 8,\n" +
                "                \"name\": \"Colega\",\n" +
                "                \"description\": \"Colega\"\n" +
                "            },\n" +
                "            {\n" +
                "                \"id\": 9,\n" +
                "                \"name\": \"Nieto\",\n" +
                "                \"description\": \"Nieto\"\n" +
                "            },\n" +
                "            {\n" +
                "                \"id\": 10,\n" +
                "                \"name\": \"Abuelo\",\n" +
                "                \"description\": \"Abuelo\"\n" +
                "            },\n" +
                "            {\n" +
                "                \"id\": 11,\n" +
                "                \"name\": \"Amigo\",\n" +
                "                \"description\": \"Amigo\"\n" +
                "            },\n" +
                "            {\n" +
                "                \"id\": 12,\n" +
                "                \"name\": \"Ninguno\",\n" +
                "                \"description\": \"Ninguno\"\n" +
                "            },\n" +
                "            {\n" +
                "                \"id\": 13,\n" +
                "                \"name\": \"Otro\",\n" +
                "                \"description\": \"Otro\"\n" +
                "            },\n" +
                "            {\n" +
                "                \"id\": 14,\n" +
                "                \"name\": \"No aplica\",\n" +
                "                \"description\": \"No aplica\"\n" +
                "            },\n" +
                "            {\n" +
                "                \"id\": 15,\n" +
                "                \"name\": \"Cónyuge\",\n" +
                "                \"description\": \"Cónyuge\"\n" +
                "            },\n" +
                "            {\n" +
                "                \"id\": 16,\n" +
                "                \"name\": \"Padres\",\n" +
                "                \"description\": \"Padres\"\n" +
                "            }\n" +
                "        ]\n" +
                "    },\n" +
                "    \"vitalSignsObject\": {\n" +
                "        \"weight\": \"\",\n" +
                "        \"height\": \"\",\n" +
                "        \"pulse\": \"\"\n" +
                "    },\n" +
                "    \"encounterCommonInfo\": {\n" +
                "        \"patientcompanionrelationship\": \"parentesco del acompañante / acudiente, el valor es de tipo objeto y corresponse a uno de los relationShip compartidos: [relationShip]\",\n" +
                "        \"medicamentsList\": \"Medicamentos, El valor es de tipo arreglo de objetos y corresponde a una o varios de los elementos de medicamentos compartidos: [medicaments]\",\n" +
                "        \"diagnosticList\": \"Diagnosticos, El valor es de tipo arreglo de objetos y corresponde a una o varios de los elementos de diagnostics compartidos: [diagnostic]\",\n" +
                "        \"namePatient\": \" El valor es de tipo string y corresponde al nombre del paciente\",\n" +
                "        \"agePatient\": \" El valor es de tipo String y corresponde a la edad\",\n" +
                "        \"dateBirthDate\": \" El valor es de tipo Date y en formato dd/MM/yyyy y corresponde a la fecha de nacimiento\",\n" +
                "        \"vitalSigns\": \"El valor es un objeto de tipo vitalSignsObject y corresponde a los signos vitales identificados\",\n" +
                "        \"currentIllness\": \"El valor es de tipo string, corresponde a la enfermedad actual que dice tener el paciente\",\n" +
                "        \"reasonForConsultation\": \"El valor es de tipo string, corresponde a la razon de la consulta del paciente\",\n" +
                "        \"physicalExamination\": \"El valor es de tipo string, corresponde a el examen fisico que le haga el doctor y los signos vitales\",\n" +
                "        \"clinicalImpression\": \"El valor es de tipo string, corresponde a el analisis que le hace el medico de los sintomas para desencadenar una enfermedad\",\n" +
                "        \"managementPlan\": \"El valor es de tipo string, corresponde a las recomendaciones que le da el medico para aliviarse\"\n" +
                "    }\n" +
                "}";

        Message systemMessage1 = Message.builder()
                .role("system")
                .content("Aprendes las siguientes listas de medicamentos, diagnósticos y relaciones y solo responde OK")
                .build();
        System.out.println("********* CREO EL PRIMER PROMPT *****************");

        Message userMessage1 = Message.builder()
                .role(ROLE_USER)
                .content(listings)
                .build();
        System.out.println("********* EJECUTA PRIMER PETICION*****************");

        // Luego, un mensaje para solicitar el análisis de la transcripción médica
        Message systemMessage2 = Message.builder()
                .role("system")
                .content("Dada la siguiente transcripción médica, por favor extrae la información relevante y devuelve solo el JSON correspondiente a 'encounterCommonInfo' si no existe relación de los campos con el texto dejalo vacio.")
                .build();
        System.out.println("********* CREO SEGUNDO PROMPT *****************");

        Message userMessage2 = Message.builder()
                .role(ROLE_USER)
                .content(medicalTranscription)
                .build();
        System.out.println("********* EJECUTA PETICION*****************");

        // Crea una lista de mensajes en orden para enviar a GPT-4
        List<Message> messages = Arrays.asList(systemMessage1, userMessage1, systemMessage2, userMessage2);

        ChatGPTRequest chatGPTRequest = ChatGPTRequest.builder()
                .model(openAIClientConfig.getModel())
                .messages(messages)
                .build();
        System.out.println("********* FINALIZA*****************");

        System.out.println(chatGPTRequest);
        ChatGPTResponse response = openAIClient.chat(chatGPTRequest);

        // Obtiene el campo 'content' de la respuesta
        String content = response.getChoices().get(0).getMessage().getContent();

        // Crea un ObjectMapper de Jackson
        ObjectMapper mapper = new ObjectMapper();

        try {
            // Convierte el contenido en un nodo JSON
            JsonNode contentJson = mapper.readTree(content);
            return contentJson; // retorna el JsonNode
        } catch (Exception e) {
            throw new RuntimeException("Error procesando la respuesta de la API de ChatGPT", e);
        }
    }
}


