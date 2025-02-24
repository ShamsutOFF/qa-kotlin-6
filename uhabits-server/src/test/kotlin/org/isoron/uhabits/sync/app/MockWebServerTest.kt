
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import io.ktor.http.HttpStatusCode
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer

class MockWebServerTest : StringSpec({

    // Объявляем переменные для MockWebServer и OkHttpClient
    lateinit var mockWebServer: MockWebServer
    lateinit var client: OkHttpClient

    // Настройка перед запуском тестов
    beforeSpec {
        // Инициализируем MockWebServer и запускаем его
        mockWebServer = MockWebServer()
        mockWebServer.start()

        // Инициализируем OkHttpClient
        client = OkHttpClient()
    }

    // Очистка после завершения тестов
    afterSpec {
        // Останавливаем MockWebServer
        mockWebServer.shutdown()
    }

    // Тест для GET /habit
    "GET /habit should return list of habits" {
        // Настраиваем mock-ответ
        val mockResponse = MockResponse()
            .setBody("""[{"id": 1, "name": "Не забывать про состояние базы данных"}]""")
            .setResponseCode(200)
        mockWebServer.enqueue(mockResponse)

        // Создаем запрос
        val request = Request.Builder()
            .url(mockWebServer.url("/habit"))
            .get()
            .build()

        // Выполняем запрос
        val response = client.newCall(request).execute()

        // Проверяем результат
        response.code shouldBe 200
        response.body?.string() shouldBe """[{"id": 1, "name": "Не забывать про состояние базы данных"}]"""
        // Проверяем, что сервер получил правильный запрос
        val recordedRequest = mockWebServer.takeRequest()
        println("@@@ recordedRequest = $recordedRequest")
        recordedRequest.method shouldBe "GET"
    }

    // Тест для POST /habit
    "POST /habit should add a new habit" {
        // Настраиваем mock-ответ
        val mockResponse = MockResponse().setResponseCode(201)
        mockWebServer.enqueue(mockResponse)

        // Создаем тело запроса
        val body = """{"id": 1, "name": "Не забывать про состояние базы данных"}"""
            .toRequestBody("application/json".toMediaType())

        // Создаем запрос с методом POST
        val request = Request.Builder()
            .url(mockWebServer.url("/habit"))
            .post(body) // Указываем метод POST
            .build()

        // Выполняем запрос
        val response = client.newCall(request).execute()

        // Проверяем, что запрос был выполнен корректно
        response.code shouldBe 201

        // Проверяем, что сервер получил правильный запрос
        val recordedRequest = mockWebServer.takeRequest()
        recordedRequest.method shouldBe "POST"
        println("@@@ recordedRequest = $recordedRequest")
        recordedRequest.body.readUtf8() shouldBe """{"id": 1, "name": "Не забывать про состояние базы данных"}"""
    }

    // Тест для DELETE /habit/:id
    "DELETE /habit/:id should remove a habit" {
        // Настраиваем mock-ответ
        val mockResponse = MockResponse().setResponseCode(204)
        mockWebServer.enqueue(mockResponse)

        // Создаем запрос с методом DELETE
        val request = Request.Builder()
            .url(mockWebServer.url("/habit/1"))
            .delete() // Указываем метод DELETE
            .build()

        // Выполняем запрос
        val response = client.newCall(request).execute()

        // Проверяем, что запрос был выполнен корректно
        response.code shouldBe 204

        // Проверяем, что сервер получил правильный запрос
        val recordedRequest = mockWebServer.takeRequest()
        println("@@@ recordedRequest = $recordedRequest")
        recordedRequest.method shouldBe "DELETE"
        recordedRequest.path shouldBe "/habit/1"
    }
})