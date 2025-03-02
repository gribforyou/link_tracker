package backend.academy.scrapper.repository;

import backend.academy.SavedLinkDto;

public record PairLinkId(SavedLinkDto savedLink, long chatId) {}
