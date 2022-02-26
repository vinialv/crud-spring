package br.com.projedata.testeIniflex.cadastros.controle;

import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import br.com.projedata.testeIniflex.cadastros.dominio.Pessoa;
import br.com.projedata.testeIniflex.cadastros.dominio.PessoaRepositorio;

@Controller
public class PessoaControle {
	
	private PessoaRepositorio pessoaRepo;

	public PessoaControle(PessoaRepositorio pessoaRepo) {
		this.pessoaRepo = pessoaRepo;
	}
	
	@GetMapping("/cadastros/pessoas")
	public String pessoas(Model model) {
		model.addAttribute("listaPessoas", pessoaRepo.findAll());
		return "cadastros/pessoas/index";		
	}
	
	@GetMapping("/cadastros/pessoas/nova")
	public String novaPessoa(@ModelAttribute("pessoa") Pessoa pessoa){
		return "cadastros/pessoas/form";
	}
	
	@PostMapping("cadastros/pessoas/salvar")
	public String salvarPessoa(@ModelAttribute("pessoa") Pessoa pessoa) {
		pessoaRepo.save(pessoa);
		return "redirect:/cadastros/pessoas";
	}
	
	@GetMapping("cadastros/pessoas/{id}")
	public String alterarPessoa(@PathVariable("id") long id, Model model) {
		Optional<Pessoa> pessoaOpt = pessoaRepo.findById(id);
		if(pessoaOpt.isEmpty()) {
			throw new IllegalArgumentException("Pessoa invalida");
		}
		
		model.addAttribute("pessoa", pessoaOpt.get());
		return "cadastros/pessoas/form";
	}
	
	@GetMapping("/cadastros/pessoas/excluir/{id}")
	public String excluirPessoa(@PathVariable("id") long id) {
			Optional<Pessoa> pessoaOpt = pessoaRepo.findById(id);
			if(pessoaOpt.isEmpty()) {
				throw new IllegalArgumentException("Pessoa invalida");
			}
			
			pessoaRepo.delete(pessoaOpt.get());
			return "redirect:/cadastros/pessoas";
	}
}
