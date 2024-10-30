package com.t3h.e_commerce.service;

import org.springframework.core.io.Resource;

public interface IFileService {
    public Resource loadFile(String filename);
    public Resource getImg(String fileName);
}
