document.addEventListener('DOMContentLoaded', () => {
    let dadosDoJogo;
    let faseAtual;
    const collectedItems = new Map();
    let totalItemsToCollect = 0;
    let draggedToken = null;

    const readingSection = document.getElementById('reading-section');
    const questionnaireSection = document.getElementById('questionnaire-section');
    const inventoryList = document.getElementById('inventory-list');
    const proceedBtn = document.getElementById('proceed-btn');
    const wordBank = document.getElementById('word-bank-questionnaire');
    const checkBtn = document.getElementById('check-btn');
    const resultMessage = document.getElementById('result-message');

    const faseTituloEl = document.querySelector('#reading-section h3');
    const faseTextoEl = document.querySelector('#reading-section p');
    const fillInBlanksEl = document.querySelector('.fill-in-blanks');
    const imageSlotsEl = document.querySelector('.image-slots');

    fetch('/aquapedia/scripts/fases_aquapedia.json')
        .then(response => {
            if (!response.ok) { throw new Error('Erro de rede ao buscar o JSON'); }
            return response.json();
        })
        .then(data => {
            dadosDoJogo = data;
            const urlParams = new URLSearchParams(window.location.search);
            const faseId = urlParams.get('id') || 1;
            carregarFase(faseId);
        })
        .catch(error => {
            console.error('Erro ao carregar o JSON da Aquapédia:', error);
            faseTituloEl.textContent = "Erro ao carregar dados da fase!";
            faseTextoEl.textContent = "Verifique se o arquivo 'fases_aquapedia.json' está no local correto e se o formato do JSON é válido.";
        });

    function carregarFase(id) {
        faseAtual = dadosDoJogo.fases.find(f => f.id_fase == id);

        if (!faseAtual) {
            faseTituloEl.textContent = "Fase não encontrada!";
            return;
        }

        const urlParams = new URLSearchParams(window.location.search);
        if (urlParams.get('debug') === 'true') {
            console.warn("⚠️ MODO DEBUG ATIVADO ⚠️");
            faseAtual.palavras_coletadas.forEach(palavra => {
                let tipo = 'word';
                const animalStrings = new Set();
                faseAtual.questionario.slots_imagem.forEach(slotId => {
                    animalStrings.add(faseAtual.questionario.respostas_corretas[slotId]);
                });
                tipo = animalStrings.has(palavra) ? 'animal' : 'word';

                const mockHTML = `<mark class="collectible-item collected" data-type="${tipo}" data-word="${palavra}">${palavra}</mark>`;
                collectedItems.set(palavra, mockHTML);
            });

            proceedBtn.disabled = false;
            proceedBtn.textContent = '[DEBUG] Ir para Questionário';
        }

        if (!urlParams.get('debug')) {
            collectedItems.clear();
            proceedBtn.disabled = true;
            proceedBtn.textContent = 'Coletar todos os itens para continuar';
        }

        inventoryList.innerHTML = '';
        if (urlParams.get('debug') === 'true') {
            collectedItems.forEach((html, palavra) => {
                const li = document.createElement('li');
                li.textContent = palavra;
                inventoryList.appendChild(li);
            });
        }

        resultMessage.textContent = '';

        construirTextoLeitura();
        construirQuestionario();
        iniciarColetaListeners();
    }

    function construirTextoLeitura() {
        const { texto_fase, palavras_coletadas, questionario } = faseAtual;
        faseTituloEl.textContent = `Fase ${faseAtual.id_fase}: ${palavras_coletadas[0]}`;

        const animalStrings = new Set();
        questionario.slots_imagem.forEach(slotId => {
            animalStrings.add(questionario.respostas_corretas[slotId]);
        });

        const palavrasOrdenadas = [...palavras_coletadas].sort((a, b) => b.length - a.length);

        let textoTemporario = texto_fase;
        const placeholders = {};

        palavrasOrdenadas.forEach((palavra, index) => {
            const placeholder = `__CODE_${index}__`;
            placeholders[placeholder] = {
                palavraOriginal: palavra,
                tipo: animalStrings.has(palavra) ? 'animal' : 'word'
            };
            const regex = new RegExp(`(?<!\\p{L})${palavra}(?!\\p{L})`, 'gui');
            textoTemporario = textoTemporario.replace(regex, placeholder);
        });

        let textoFinal = textoTemporario;
        for (const [code, dados] of Object.entries(placeholders)) {
            const regexPlaceholder = new RegExp(code, 'g');
            textoFinal = textoFinal.replace(regexPlaceholder,
                `<mark class="collectible-item" data-type="${dados.tipo}" data-word="${dados.palavraOriginal}">${dados.palavraOriginal}</mark>`
            );
        }

        faseTextoEl.innerHTML = textoFinal;
    }

    function construirQuestionario() {
        const { questionario, nome_imagens_animais } = faseAtual;
        fillInBlanksEl.innerHTML = '';
        imageSlotsEl.innerHTML = '';

        questionario.texto_com_lacunas.forEach(item => {
            if (item.tipo === 'texto') {
                fillInBlanksEl.appendChild(document.createTextNode(item.conteudo));
            } else if (item.tipo === 'lacuna') {
                const span = document.createElement('span');
                span.className = 'blank-space word-slot';
                span.dataset.slot = item.id_slot;
                fillInBlanksEl.appendChild(span);
            }
        });

        questionario.slots_imagem.forEach((slotId, index) => {
            const containerDiv = document.createElement('div');
            containerDiv.className = 'image-text';

            const img = document.createElement('img');
            img.className = 'quest-image';
            img.src = `./img/fases/${nome_imagens_animais[index]}`;
            img.alt = `Imagem de ${slotId}`;

            const slotSpan = document.createElement('span');
            slotSpan.className = 'blank-space animal-slot';
            slotSpan.dataset.slot = slotId;

            containerDiv.appendChild(img);
            containerDiv.appendChild(slotSpan);
            imageSlotsEl.appendChild(containerDiv);
        });

        initDragDropListeners();
    }

    function iniciarColetaListeners() {
        const allCollectibleElements = readingSection.querySelectorAll('.collectible-item');
        const uniqueWords = new Set();
        allCollectibleElements.forEach(el => uniqueWords.add(el.textContent));
        totalItemsToCollect = uniqueWords.size;

        allCollectibleElements.forEach(item => {
            item.addEventListener('click', () => {
                const word = item.textContent;
                if (collectedItems.has(word)) return;

                const sameWordElements = readingSection.querySelectorAll(`.collectible-item[data-word="${word}"]`);
                sameWordElements.forEach(el => el.classList.add('collected'));

                collectedItems.set(word, item.outerHTML);
                const inventoryItem = document.createElement('li');
                inventoryItem.textContent = word;
                inventoryList.appendChild(inventoryItem);

                if (collectedItems.size === totalItemsToCollect) {
                    proceedBtn.disabled = false;
                    proceedBtn.textContent = 'Tudo pronto! Iniciar Questionário';
                }
            });
        });
    }

    proceedBtn.addEventListener('click', () => {
        if (proceedBtn.disabled) return;
        readingSection.classList.remove('active');
        questionnaireSection.classList.add('active');
        populateWordBank();
    });

    function populateWordBank() {
        wordBank.innerHTML = '';
        collectedItems.forEach(itemHTML => {
            createTokenInBank(itemHTML);
        });
    }

    function createTokenInBank(itemHTML) {
        const tempDiv = document.createElement('div');
        tempDiv.innerHTML = itemHTML;
        const itemNode = tempDiv.firstChild;

        const token = document.createElement('div');
        token.className = 'word-token';
        token.draggable = true;
        token.textContent = itemNode.textContent;
        token.dataset.word = itemNode.textContent;
        token.dataset.type = itemNode.dataset.type;

        token.addEventListener('dragstart', handleDragStart);
        token.addEventListener('dragend', handleDragEnd);
        wordBank.appendChild(token);
    }

    function handleDragStart(e) {
        draggedToken = e.target;
        setTimeout(() => e.target.classList.add('hidden'), 0);
    }

    function handleDragEnd(e) {
        if (draggedToken) {
            draggedToken.classList.remove('hidden');
        }
        draggedToken = null;
    }

    function initDragDropListeners() {
        const slots = document.querySelectorAll('.blank-space, .image-slot');
        slots.forEach(slot => {
            slot.addEventListener('dragover', e => {
                e.preventDefault();
                slot.classList.add('over');
            });
            slot.addEventListener('dragleave', () => slot.classList.remove('over'));
            slot.addEventListener('drop', handleDrop);
        });
    }

    function handleDrop(e) {
        e.preventDefault();
        const slot = e.currentTarget;
        slot.classList.remove('over');

        if (!draggedToken) return;

        const word = draggedToken.dataset.word;
        const type = draggedToken.dataset.type;
        const isWordSlot = slot.classList.contains('word-slot');
        const isAnimalSlot = slot.classList.contains('animal-slot');

        if (isWordSlot && type !== 'word') return;
        if (isAnimalSlot && type !== 'animal') return;

        if (slot.dataset.value) {
            const oldWord = slot.dataset.value;
            const oldItemHTML = collectedItems.get(oldWord);
            if (oldItemHTML) createTokenInBank(oldItemHTML);
        }

        slot.textContent = word;
        slot.dataset.value = word;
        slot.classList.add('clickable');
        slot.addEventListener('click', returnItemToBank);

        draggedToken.remove();
        draggedToken = null;
    }

    function returnItemToBank(e) {
        const slot = e.currentTarget;
        const word = slot.dataset.value;
        if (!word) return;

        const itemHTML = collectedItems.get(word);
        if (itemHTML) {
            createTokenInBank(itemHTML);
        }

        slot.dataset.value = '';
        slot.textContent = '';
        slot.classList.remove('clickable', 'correct', 'incorrect');
        slot.removeEventListener('click', returnItemToBank);
    }

    checkBtn.addEventListener('click', () => {
        const correctAnswers = faseAtual.questionario.respostas_corretas;
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

        if (allCorrect) {
            resultMessage.textContent = "Parabéns, todas as respostas estão corretas!";
            resultMessage.style.color = 'var(--correct-green, green)';
        } else {
            resultMessage.textContent = "Ops, parece que algo não está certo. Tente novamente!";
            resultMessage.style.color = 'var(--incorrect-red, red)';
        }
    });
});