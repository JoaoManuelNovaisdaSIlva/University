using Microsoft.EntityFrameworkCore;
using Microsoft.Extensions.DependencyInjection;
using Feira_Facil.Data;
var builder = WebApplication.CreateBuilder(args);
builder.Services.AddDbContext<Feira_FacilContext>(options =>
    options.UseSqlServer(builder.Configuration.GetConnectionString("Feira_FacilContext") ?? throw new InvalidOperationException("Connection string 'Feira_FacilContext' not found.")));

// Add services to the container.
builder.Services.AddControllersWithViews();

builder.Services.AddSession(options =>
{
    options.Cookie.Name = ".EasyFeira.Required";
    options.IdleTimeout = TimeSpan.FromSeconds(86400);
    options.Cookie.IsEssential = true;
});



var app = builder.Build();

// Configure the HTTP request pipeline.
if (!app.Environment.IsDevelopment())
{
    app.UseExceptionHandler("/Home/Error");
}
app.UseStaticFiles();

app.UseRouting();

app.UseAuthorization();

app.UseSession();

app.MapControllerRoute(
    name: "default",
    pattern: "{controller=Home}/{action=Index}/{id?}");

app.Run();
