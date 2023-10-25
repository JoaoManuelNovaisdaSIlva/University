using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Mvc;
using Microsoft.AspNetCore.Mvc.Rendering;
using Microsoft.EntityFrameworkCore;
using Feira_Facil.Data;
using Feira_Facil.Models;

namespace Feira_Facil.Controllers
{
    public class ProdutosController : Controller
    {
        private readonly Feira_FacilContext _context;

        public ProdutosController(Feira_FacilContext context)
        {
            _context = context;
        }

        // GET: Produtoes
        public async Task<IActionResult> List(int? id)
        {
              return View(await _context.Produto.Where(p => p.idStand == id).ToListAsync());
        }

        // GET: Produtoes/Details/5
        public async Task<IActionResult> Details(int? id)
        {
            if (id == null || _context.Produto == null)
            {
                return NotFound();
            }

            var produto = await _context.Produto
                .FirstOrDefaultAsync(m => m.IdProduto == id);
            if (produto == null)
            {
                return NotFound();
            }

            return View(produto);
        }

        // GET: Produtoes/Create
        public async Task<IActionResult> Create(int? id)
        {
            if (HttpContext.Session.GetString("VendedorLogin") == null)
                return RedirectToAction("Login", "Vendedores");
            if(id == null)
            {
                return NotFound();
            }

            String vendedor = HttpContext.Session.GetString("VendedorLogin");
            String[] paramsVendedor = vendedor.Split(",");
            Console.Write(id);
            ViewBag.IdStand = id;
            ViewBag.Id_Vendedor = paramsVendedor[0];
            return View();
        }

        // POST: Produtoes/Create
        // To protect from overposting attacks, enable the specific properties you want to bind to.
        // For more details, see http://go.microsoft.com/fwlink/?LinkId=317598.
        [HttpPost]
        [ValidateAntiForgeryToken]
        public async Task<IActionResult> Create([Bind("IdProduto,idVendedor,idStand,preco,imagens,nome,stock")] Produto produto)
        {
            if (HttpContext.Session.GetString("VendedorLogin") == null)
                return RedirectToAction("Login", "Vendedores");
            if (ModelState.IsValid)
            {
                _context.Add(produto);
                await _context.SaveChangesAsync();
                return RedirectToAction("Stands", "Vendedores");
            }
            return View(produto);
        }

        // GET: Produtoes/Edit/5
        public async Task<IActionResult> Edit(int? id)
        {
            if (HttpContext.Session.GetString("VendedorLogin") == null)
                return RedirectToAction("Login", "Vendedores");
            if (id == null || _context.Produto == null)
            {
                return NotFound();
            }

            var produto = await _context.Produto.FindAsync(id);
            if (produto == null)
            {
                return NotFound();
            }
            return View(produto);
        }

        // POST: Produtoes/Edit/5
        // To protect from overposting attacks, enable the specific properties you want to bind to.
        // For more details, see http://go.microsoft.com/fwlink/?LinkId=317598.
        [HttpPost]
        [ValidateAntiForgeryToken]
        public async Task<IActionResult> Edit(int id, [Bind("IdProduto,idVendedor,idStand,preco,imagens")] Produto produto)
        {
            if (HttpContext.Session.GetString("VendedorLogin") == null)
                return RedirectToAction("Login", "Vendedores");
            if (id != produto.IdProduto)
            {
                return NotFound();
            }

            if (ModelState.IsValid)
            {
                try
                {
                    _context.Update(produto);
                    await _context.SaveChangesAsync();
                }
                catch (DbUpdateConcurrencyException)
                {
                    if (!ProdutoExists(produto.IdProduto))
                    {
                        return NotFound();
                    }
                    else
                    {
                        throw;
                    }
                }
                return RedirectToAction(nameof(Index));
            }
            return View(produto);
        }

        // GET: Produtoes/Delete/5
        public async Task<IActionResult> Delete(int? id)
        {
            if (HttpContext.Session.GetString("VendedorLogin") == null)
                return RedirectToAction("Login", "Vendedores");
            if (_context.Produto == null)
            {
                return Problem("Entity set 'Feira_FacilContext.Produto'  is null.");
            }
            var produto = await _context.Produto.FindAsync(id);
            if (produto != null)
            {
                _context.Produto.Remove(produto);
            }

            await _context.SaveChangesAsync();
            return RedirectToAction("Stands", "Vendedores");
        }



        private bool ProdutoExists(int id)
        {
          return _context.Produto.Any(e => e.IdProduto == id);
        }
    }
}
