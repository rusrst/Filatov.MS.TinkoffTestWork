package com.example.tinkofftestwork.data.json

import com.example.tinkofftestwork.data.DataClass
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class JsonParserTest{
    @Test
    fun jsonTestNullData(){
        val data = JsonParser.getList(null, 0)
        assertEquals(null, data)
    }
    @Test
    fun jsonTestCorruptedData(){
        val data = JsonParser.getList("{\"result\":[{\"id\":17090,\"description\":\"Unhandled exception\",\"votes\":5,\"author\":\"xakep\",\"date\":\"Oct 26, 2021 10:05:22 AM\",\"gifURL\":\"http://static.devli.ru/public/images/gifs/202109/518d3a2b-42eb-4b05-8e81-a5329a1d8288.gif\",\"gifSize\":8031076,\"previewURL\":\"https://static.devli.ru/public/images/previews/202109/518d3a2b-42eb-4b05-8e81-a5329a1d8288.jpg\",\"videoURL\":\"http://static.devli.ru/public/images/v/202109/518d3a2b-42eb-4b05-8e81-a5329a1d8288.mp4\",\"videoPath\":\"/public/images/v/202109/518d3a2b-42eb-4b05-8e81-a5329a1d8288.mp4\"" +
                ",\"videoSize\":970357,\"type\":\"gif\"," +
                "\"width\":\"480\",\"height\":\"592\",\"commentsCount\":1,\"fileSize\":8031076,\"canVote\":false}", 0)
        assertEquals(null, data)
    }
    @Test
    fun jsonCorrectData(){
        val data = JsonParser.getList("{\n" +
                "  \"result\": [\n" +
                "    {\n" +
                "      \"id\": 17090,\n" +
                "      \"description\": \"Unhandled exception\",\n" +
                "      \"votes\": 5,\n" +
                "      \"author\": \"xakep\",\n" +
                "      \"date\": \"Oct 26, 2021 10:05:22 AM\",\n" +
                "      \"gifURL\": \"http://static.devli.ru/public/images/gifs/202109/518d3a2b-42eb-4b05-8e81-a5329a1d8288.gif\",\n" +
                "      \"gifSize\": 8031076,\n" +
                "      \"previewURL\": \"https://static.devli.ru/public/images/previews/202109/518d3a2b-42eb-4b05-8e81-a5329a1d8288.jpg\",\n" +
                "      \"videoURL\": \"http://static.devli.ru/public/images/v/202109/518d3a2b-42eb-4b05-8e81-a5329a1d8288.mp4\",\n" +
                "      \"videoPath\": \"/public/images/v/202109/518d3a2b-42eb-4b05-8e81-a5329a1d8288.mp4\",\n" +
                "      \"videoSize\": 970357,\n" +
                "      \"type\": \"gif\",\n" +
                "      \"width\": \"480\",\n" +
                "      \"height\": \"592\",\n" +
                "      \"commentsCount\": 1,\n" +
                "      \"fileSize\": 8031076,\n" +
                "      \"canVote\": false\n" +
                "    },\n" +
                "    {\n" +
                "      \"id\": 17088,\n" +
                "      \"description\": \"Накануне дедлайна\",\n" +
                "      \"votes\": 5,\n" +
                "      \"author\": \"w3lifer\",\n" +
                "      \"date\": \"May 17, 2021 12:47:20 PM\",\n" +
                "      \"gifURL\": \"http://static.devli.ru/public/images/gifs/202105/338eec95-f956-4aa6-8844-219166979cc2.gif\",\n" +
                "      \"gifSize\": 637391,\n" +
                "      \"previewURL\": \"https://static.devli.ru/public/images/previews/202105/338eec95-f956-4aa6-8844-219166979cc2.jpg\",\n" +
                "      \"videoURL\": \"http://static.devli.ru/public/images/v/202105/338eec95-f956-4aa6-8844-219166979cc2.mp4\",\n" +
                "      \"videoPath\": \"/public/images/v/202105/338eec95-f956-4aa6-8844-219166979cc2.mp4\",\n" +
                "      \"videoSize\": 172526,\n" +
                "      \"type\": \"gif\",\n" +
                "      \"width\": \"232\",\n" +
                "      \"height\": \"154\",\n" +
                "      \"commentsCount\": 0,\n" +
                "      \"fileSize\": 637391,\n" +
                "      \"canVote\": false\n" +
                "    }\n" +
                "  ],\n" +
                "  \"totalCount\": 12924\n" +
                "}", 0)
        val list = listOf<DataClass>(DataClass("Unhandled exception",
            "http://static.devli.ru/public/images/gifs/202109/518d3a2b-42eb-4b05-8e81-a5329a1d8288.gif", 0),
        DataClass("Накануне дедлайна",
            "http://static.devli.ru/public/images/gifs/202105/338eec95-f956-4aa6-8844-219166979cc2.gif", 0)
        )
        assertEquals(list, data)
    }
}