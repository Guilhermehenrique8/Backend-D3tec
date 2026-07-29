INSERT INTO categoria (id, nome, slug) VALUES
    (nextval('categoria_id_seq'), 'Desenvolvimento Web', 'desenvolvimento-web'),
    (nextval('categoria_id_seq'), 'Empreendedorismo', 'empreendedorismo'),
    (nextval('categoria_id_seq'), 'Empresa Junior', 'empresa-junior'),
    (nextval('categoria_id_seq'), 'Ciencia da Computacao', 'ciencia-da-computacao'),
    (nextval('categoria_id_seq'), 'Projetos da D3TEC', 'projetos-d3tec'),
    (nextval('categoria_id_seq'), 'Dicas para Negocios', 'dicas-para-negocios'),
    (nextval('categoria_id_seq'), 'Inovacao', 'inovacao'),
    (nextval('categoria_id_seq'), 'Carreira em Tecnologia', 'carreira-em-tecnologia')
ON CONFLICT (slug) DO NOTHING;
