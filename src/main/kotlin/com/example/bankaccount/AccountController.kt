package com.example.bankaccount

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("account")
class AccountController(val repository: AccountRepository) {

    @PostMapping
    fun create(@RequestBody account: Account): ResponseEntity<Account> {
        val accountSave = repository.save(account)
        return ResponseEntity.ok(accountSave)
    }

    @GetMapping
    fun read() = ResponseEntity.ok(repository.findAll())

    @PutMapping("{document}")
    fun update(@PathVariable document: String, @RequestBody account: Account): ResponseEntity<Account> {
        val possivelAccount = repository.findByDocument(document)
        val accountSave =
            possivelAccount.orElseThrow { java.lang.RuntimeException("Account document: $document not found!") }
        val account = repository.save(accountSave.copy(name = account.name, balance = account.balance))
        return ResponseEntity.ok(account)
    }

    @DeleteMapping("{document}")
    fun delete(@PathVariable document: String) = repository.findByDocument(document)
                                                            .ifPresent { repository.delete(it) }
}