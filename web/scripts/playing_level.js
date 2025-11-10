document.addEventListener('DOMContentLoaded', () => {
    const readingSection = document.getElementById('reading-section');
    const collectibleItems = readingSection.querySelectorAll('.collectible-item');
    const inventoryList = document.getElementById('inventory-list');
    const proceedBtn = document.getElementById('proceed-btn');
    const collectedItems = new Set();
    const totalItemsToCollect = collectibleItems.length;

    const correctAnswers = {
        'terra': 'planeta', // Oculto no texto original, para forçar o raciocínio
        'america': 'América',
        'australia': 'Austrália',
        'baleia-azul': 'Baleia-Azul',
        'orca': 'Orca',
        'beluga': 'Beluga',
        'cachalote': 'Cachalote'
    };

    collectibleItems.forEach(item => {
        item.addEventListener('click', () => {
            const word = item.textContent;
            if (!collectedItems.has(word)) {
                collectedItems.add(item.outerHTML); 
              item.classList.add('collected');
                
                const inventoryItem = document.createElement('li');
                inventoryItem.textContent = word;
                inventoryList.appendChild(inventoryItem);
                
                if (collectedItems.size === totalItemsToCollect) {
                    proceedBtn.disabled = false;
                    proceedBtn.textContent = 'Tudo pronto! Iniciar Questionário';
                }
           }
        });
    });

    proceedBtn.addEventListener('click', () => {
        if (proceedBtn.disabled) return;
        readingSection.classList.remove('active');
        document.getElementById('questionnaire-section').classList.add('active');
        populateWordBankAndInitDragDrop();
    });

    function populateWordBankAndInitDragDrop() {
        const wordBank = document.getElementById('word-bank-questionnaire');
        wordBank.innerHTML = '';

        collectedItems.forEach(itemHTML => {
            const tempDiv = document.createElement('div');
            tempDiv.innerHTML = itemHTML;
            const itemNode = tempDiv.firstChild;

            const token = document.createElement('div');
            token.className = 'word-token';
            token.draggable = true;
            token.textContent = itemNode.textContent;
            token.dataset.word = itemNode.textContent;
            token.dataset.type = itemNode.dataset.type; // word ou animal
            wordBank.appendChild(token);
        });
        
        initDragDrop();
    }
    
    function initDragDrop() {
        const tokens = document.querySelectorAll('#word-bank-questionnaire .word-token');
        const slots = document.querySelectorAll('.blank-space, .image-slot');
            let draggedToken = null;

            tokens.forEach(token => {
                token.addEventListener('dragstart', e => {
                    draggedToken = e.target;
                    setTimeout(() => e.target.classList.add('hidden'), 0);
                });
                token.addEventListener('dragend', e => {
                    if (!draggedToken) e.target.remove(); // Remove o token do banco se foi solto com sucesso
                });
            });
            
            slots.forEach(slot => {
                slot.addEventListener('dragover', e => { e.preventDefault(); slot.classList.add('over'); });
                slot.addEventListener('dragleave', () => slot.classList.remove('over'));
                slot.addEventListener('drop', e => {
                    e.preventDefault();
                    slot.classList.remove('over');
                    if (draggedToken) {
                        const word = draggedToken.dataset.word;
                        const type = draggedToken.dataset.type;

                        if (slot.classList.contains('blank-space') && type === 'word') {
                            slot.textContent = word;
                            slot.dataset.value = word;
                            draggedToken = null;
                
                        } else if (slot.classList.contains('image-slot') && type === 'animal') {
                            // Substitua por imagens reais. Usando pravatar como placeholder.
                            slot.style.backgroundImage = `url(https://mergulholivre.com.br/wp-content/uploads/2023/07/Tubarao-Mako-peixe-de-agua-salgada-foto-6.webp)`;
                            slot.dataset.value = word;
                            draggedToken = null;
                        }
                    }
                });
            });
        }

        document.getElementById('check-btn').addEventListener('click', () => {
            let allCorrect = true;
            const slots = document.querySelectorAll('.blank-space, .image-slot');
            
            slots.forEach(slot => {
                const slotId = slot.dataset.slot;
                const userValue = slot.dataset.value || '';
                
                if (correctAnswers[slotId] && userValue === correctAnswers[slotId]) {
                    slot.classList.add('correct');
                    slot.classList.remove('incorrect');
                } else {
                    slot.classList.add('incorrect');
                    slot.classList.remove('correct');
                    allCorrect = false;
                }
            });
            
            const resultMessage = document.getElementById('result-message');
            if(allCorrect) {
                resultMessage.textContent = "Parabéns, todas as respostas estão corretas!";
                resultMessage.style.color = 'var(--correct-green)';
            } else {
                 resultMessage.textContent = "Ops, parece que algo não está certo. Tente novamente!";
                 resultMessage.style.color = 'var(--incorrect-red)';
            }
        });
    });

