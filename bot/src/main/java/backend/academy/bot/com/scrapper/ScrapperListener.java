package backend.academy.bot.com.scrapper;

import backend.academy.UpdateDto;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.request.SendMessage;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class ScrapperListener {
    private TelegramBot bot;

    @PostMapping("/updates")
    public void sendUpdate(@RequestBody UpdateDto updateDto) {
        for(var id : updateDto.chatIds()){
            bot.execute(new SendMessage(id, updateDto.description()));
        }
    }
}
